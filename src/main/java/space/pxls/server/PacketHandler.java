package space.pxls.server;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.typesafe.config.Config;
import io.undertow.websockets.core.WebSocketChannel;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.LookupTranslator;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import space.pxls.App;
import space.pxls.data.DBChatMessage;
import space.pxls.data.DBPixelPlacement;
import space.pxls.server.packets.socket.*;
import space.pxls.user.*;
import space.pxls.util.TextFilter;
import space.pxls.util.RateLimitFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

public class PacketHandler {
    private UndertowServer server;
    private int numAllCons = 0;

    public int getCooldown() {
        Config config = App.getConfig();

        String cooldownType = config.getString("cooldownType").toLowerCase();
        if (cooldownType.equalsIgnoreCase("activity")) {
            double x = server.getNonIdledUsersCount();
            double s = config.getDouble("activityCooldown.steepness");
            double u = config.getDouble("activityCooldown.userOffset");
            double t = config.getDouble("activityCooldown.globalOffset");

            // Formula by Atomic10 and c4rt
            // https://www.desmos.com/calculator/sgphb1abzi
            double cooldown = s * Math.sqrt(x + u) + t;

            double multiplier = config.getDouble("activityCooldown.multiplier");
            cooldown *= multiplier;

            return (int) Math.abs(cooldown);
        } else {
            return (int) config.getDuration("staticCooldown.time", TimeUnit.SECONDS);
        }
    }

    public PacketHandler(UndertowServer server) {
        this.server = server;
    }

    public void userdata(WebSocketChannel channel, User user) {
        if (user != null) {
            server.send(channel, new ServerNotifyUserInfo(
                    user.getName(),
                    user.getLogin(),
                    user.getAllRoles(),
                    user.getPixelCount(),
                    user.getAllTimePixelCount(),
                    user.isBanned(),
                    user.getBanExpiryTime(),
                    user.getBanReason(),
                    user.getLogin().split(":")[0],
                    user.isOverridingCooldown(),
                    user.isChatbanned(),
                    App.getDatabase().getChatBanReason(user.getId()),
                    user.isPermaChatbanned(),
                    user.getChatbanExpiryTime(),
                    user.isRenameRequested(true),
                    user.getDiscordName(),
                    user.getChatNameColor()
            ));
            sendAvailablePixels(channel, user, "auth");
        }
    }

    public void connect(WebSocketChannel channel, User user) {
        if (user != null) {
            userdata(channel, user);
            sendCooldownData(channel, user);
            user.flagForCaptcha();
            server.addAuthedUser(user);

            user.setInitialAuthTime(System.currentTimeMillis());
            user.tickStack(false); // pop the whole pixel stack
            sendAvailablePixels(channel, user, "connect");
        }
        numAllCons++;

        updateUserData();
    }

    public void disconnect(WebSocketChannel channel, User user) {
        if (user != null && user.getConnections().size() == 0) {
            server.removeAuthedUser(user);
        }
        numAllCons--;

        updateUserData();
    }

    public void accept(WebSocketChannel channel, User user, Object obj, String ip) {
        if (user == null) return;
        if (obj instanceof ClientPlace && user.hasPermission("board.place")) handlePlace(channel, user, ((ClientPlace) obj), ip);
        if (obj instanceof ClientUndo && user.hasPermission("board.undo")) handleUndo(channel, user, ((ClientUndo) obj), ip);
        if (obj instanceof ClientCompleteCaptcha) handleCaptcha(channel, user, ((ClientCompleteCaptcha) obj));
        if (obj instanceof ClientShadowBanMe) handleShadowBanMe(channel, user, ((ClientShadowBanMe) obj));
        if (obj instanceof ClientBanMe) handleBanMe(channel, user, ((ClientBanMe) obj));
        if (obj instanceof ClientGetChatHistory && user.hasPermission("chat.history")) handleChatHistory(channel, user, ((ClientGetChatHistory) obj));
        if (obj instanceof ClientGetChatbanState) handleChatbanState(channel, user, ((ClientGetChatbanState) obj));
        if (obj instanceof ClientSendChatMessage && user.hasPermission("chat.send")) handleChatMessage(channel, user, ((ClientSendChatMessage) obj));
        if (obj instanceof ClientUpdateUser) handleClientUserUpdate(channel, user, ((ClientUpdateUser) obj));
        if (obj instanceof ClientChatLookupByUsername && user.hasPermission("chat.lookup")) handleChatLookupByUsername(channel, user, ((ClientChatLookupByUsername) obj));
        if (obj instanceof ClientChatLookupByMessageId && user.hasPermission("chat.lookup")) handleChatLookupByMessageId(channel, user, ((ClientChatLookupByMessageId) obj));
        if (obj instanceof ClientSetAdminCooldownOverride && user.hasPermission("board.cooldown.override")) handleCooldownOverride(channel, user, ((ClientSetAdminCooldownOverride) obj));
        if (obj instanceof ClientSendAdminMessage && user.hasPermission("user.alert")) handleAdminMessage(channel, user, ((ClientSendAdminMessage) obj));
    }

    private void handleAdminMessage(WebSocketChannel channel, User user, ClientSendAdminMessage obj) {
        User u = App.getUserManager().getByName(obj.username);
        if (u != null) {
            ServerAlert msg = new ServerAlert(user.getName(), escapeHtml4(obj.message));
            App.getDatabase().insertAdminLog(user.getId(), String.format("Sent an alert to %s (UID: %d) with the content: %s", u.getName(), u.getId(), escapeHtml4(obj.message)));
            for (WebSocketChannel ch : u.getConnections()) {
                server.send(ch, msg);
            }
        }
    }

    private void handleChatLookupByMessageId(WebSocketChannel channel, User user, ClientChatLookupByMessageId obj) {
        String username = null;
        if (obj.messageId != null) {
            DBChatMessage chatMessage = App.getDatabase().getChatMessageByID(obj.messageId);
            if (chatMessage != null) {
                User fromChatMessage = App.getUserManager().getByID(chatMessage.author_uid);
                username = fromChatMessage != null ? fromChatMessage.getName() : null;
            }
        }
        handleChatLookup(channel, user, username);
    }

    private void handleChatLookupByUsername(WebSocketChannel channel, User user, ClientChatLookupByUsername obj) {
        handleChatLookup(channel, user, obj.username);
    }

    private void handleChatLookup(WebSocketChannel channel, User user, String username) {
        ServerReturnChatLookup scl = username != null ? App.getDatabase().runChatLookupForUsername(username, App.getConfig().getInt("chat.chatLookupScrollbackAmount")) : null;
        server.send(channel, scl == null ? new ServerError("User doesn't exist") : scl);
    }

    private void handleShadowBanMe(WebSocketChannel channel, User user, ClientShadowBanMe obj) {
        if (!user.isBanned() && !user.isShadowBanned()) {
            App.getDatabase().insertAdminLog(user.getId(), String.format("shadowban %s with reason: self-shadowban via script; %s", user.getName(), obj.reason));
            user.shadowBan(String.format("auto-ban via script; %s", obj.reason), 999*24*3600, user);
        }
    }

    private void handleBanMe(WebSocketChannel channel, User user, ClientBanMe obj) {
        String app = obj.reason;
        App.getDatabase().insertAdminLog(user.getId(), String.format("permaban %s with reason: auto-ban via script; %s", user.getName(), app));
        user.ban(0, String.format("auto-ban via script; %s", app), 0, user);
    }

    private void handleCooldownOverride(WebSocketChannel channel, User user, ClientSetAdminCooldownOverride obj) {
        user.setOverrideCooldown(obj.override);
        sendCooldownData(user);
    }

    private void handleUndo(WebSocketChannel channel, User user, ClientUndo cu, String ip){
        boolean _canUndo = user.canUndo(true);
        if (!_canUndo || user.undoWindowPassed()) {
            return;
        }
        if (user.isShadowBanned()) {
            user.setCooldown(0);
            sendCooldownData(user);
            return;
        }
        boolean gotLock = user.tryGetUndoLock();
        if (gotLock) {
            try {
                DBPixelPlacement thisPixel = App.getDatabase().getUserUndoPixel(user);
                Optional<DBPixelPlacement> recentPixel = App.getDatabase().getPixelAt(thisPixel.x, thisPixel.y);
                if (!recentPixel.isPresent()) return;
                if (thisPixel.id != recentPixel.get().id) return;

                if (user.lastPlaceWasStack()) {
                    user.setStacked(Math.min(user.getStacked() + 1, App.getConfig().getInt("stacking.maxStacked")));
                    sendAvailablePixels(user, "undo");
                }
                user.setCooldown(0);
                DBPixelPlacement lastPixel = App.getDatabase().getPixelByID(null, thisPixel.secondaryId);
                if (lastPixel != null) {
                    App.getDatabase().putUserUndoPixel(lastPixel, user, thisPixel.id);
                    App.putPixel(lastPixel.x, lastPixel.y, lastPixel.color, user, false, ip, false, "user undo");
                    user.decreasePixelCounts();
                    broadcastPixelUpdate(lastPixel.x, lastPixel.y, lastPixel.color);
                    ackUndo(user, lastPixel.x, lastPixel.y);
                } else {
                    byte defaultColor = App.getDefaultColor(thisPixel.x, thisPixel.y);
                    App.getDatabase().putUserUndoPixel(thisPixel.x, thisPixel.y, defaultColor, user, thisPixel.id);
                    user.decreasePixelCounts();
                    App.putPixel(thisPixel.x, thisPixel.y, defaultColor, user, false, ip, false, "user undo");
                    broadcastPixelUpdate(thisPixel.x, thisPixel.y, defaultColor);
                    ackUndo(user, thisPixel.x, thisPixel.y);
                }
                sendAvailablePixels(user, "undo");
                sendCooldownData(user);
                sendPixelCountUpdate(user);
            } finally {
                user.releaseUndoLock();
            }
        }
    }

    private void handlePlace(WebSocketChannel channel, User user, ClientPlace cp, String ip) {
        if (cp.x < 0 || cp.y >= App.getWidth() || cp.y < 0 || cp.y >= App.getHeight()) return;
        if (cp.color < 0 || cp.color >= App.getConfig().getStringList("board.palette").size()) return;
        if (user.isBanned()) return;

        if (user.canPlace()) {
            boolean gotLock = user.tryGetPlacingLock();
            if (gotLock) {
                try {
                    boolean doCaptcha = App.isCaptchaEnabled();
                    if (doCaptcha) {
                        int pixels = App.getConfig().getInt("captcha.maxPixels");
                        if (pixels != 0) {
                            boolean allTime = App.getConfig().getBoolean("captcha.allTime");
                            doCaptcha = (allTime ? user.getAllTimePixelCount() : user.getPixelCount()) < pixels;
                        }
                    }
                    if (user.updateCaptchaFlagPrePlace() && doCaptcha) {
                        server.send(channel, new ServerNotifyCaptchaRequired());
                    } else {
                        int c = App.getPixel(cp.x, cp.y);
                        boolean canPlace = false;
                        if (App.getHavePlacemap()) {
                            int placemapType = App.getPlacemap(cp.x, cp.y);
                            switch (placemapType) {
                                case 0:
                                    // Allow normal placement
                                    canPlace = c != cp.color;
                                    break;
                                case 2:
                                    // Allow tendril placement
                                    int top = App.getPixel(cp.x, cp.y + 1);
                                    int left = App.getPixel(cp.x - 1, cp.y);
                                    int right = App.getPixel(cp.x + 1, cp.y);
                                    int bottom = App.getPixel(cp.x, cp.y - 1);

                                    int defaultTop = App.getDefaultColor(cp.x, cp.y + 1);
                                    int defaultLeft = App.getDefaultColor(cp.x - 1, cp.y);
                                    int defaultRight = App.getDefaultColor(cp.x + 1, cp.y);
                                    int defaultBottom = App.getDefaultColor(cp.x, cp.y - 1);
                                    if (top != defaultTop || left != defaultLeft || right != defaultRight || bottom != defaultBottom) {
                                        // The pixel has at least one other attached pixel
                                        canPlace = c != cp.color && c != 0xFF && c != -1;
                                    }
                                    break;
                            }
                        } else {
                            canPlace = c != cp.color && c != 0xFF && c != -1;
                        }
                        int c_old = c;
                        if (canPlace) {
                            int seconds = getCooldown();
                            if (c_old != 0xFF && c_old != -1 && App.getDatabase().shouldPixelTimeIncrease(user.getId(), cp.x, cp.y) && App.getConfig().getBoolean("backgroundPixel.enabled")) {
                                seconds = (int)Math.round(seconds * App.getConfig().getDouble("backgroundPixel.multiplier"));
                            }
                            if (user.isShadowBanned()) {
                                // ok let's just pretend to set a pixel...
                                App.logShadowbannedPixel(cp.x, cp.y, cp.color, user.getName(), ip);
                                ServerNotifyPlace msg = new ServerNotifyPlace(Collections.singleton(new ServerNotifyPlace.Pixel(cp.x, cp.y, cp.color)));
                                for (WebSocketChannel ch : user.getConnections()) {
                                    server.send(ch, msg);
                                }
                                if (user.canUndo(false)) {
                                    server.send(channel, new ServerNotifyCanUndo(App.getConfig().getDuration("undo.window", TimeUnit.SECONDS)));
                                }
                            } else {
                                boolean mod_action = user.isOverridingCooldown();
                                App.putPixel(cp.x, cp.y, cp.color, user, mod_action, ip, true, "");
                                App.saveMap();
                                broadcastPixelUpdate(cp.x, cp.y, cp.color);
                                ackPlace(user, cp.x, cp.y);
                                sendPixelCountUpdate(user);
                            }
                            if (!user.isOverridingCooldown()) {
                                if (user.isIdled()) {
                                    user.setIdled(false);
                                    updateUserData();
                                }
                                user.setLastPixelTime();
                                if (user.getStacked() > 0) {
                                    user.setLastPlaceWasStack(true);
                                    user.setStacked(user.getStacked()-1);
                                    sendAvailablePixels(user, "consume");
                                } else {
                                    user.setLastPlaceWasStack(false);
                                    user.setCooldown(seconds);
                                    App.getDatabase().updateUserTime(user.getId(), seconds);
                                    sendAvailablePixels(user, "consume");
                                }

                                if (user.canUndo(false)) {
                                    server.send(channel, new ServerNotifyCanUndo(App.getConfig().getDuration("undo.window", TimeUnit.SECONDS)));
                                }
                            }

                            sendCooldownData(user);
                        }
                    }
                } finally {
                    user.releasePlacingLock();
                }
            }
        }
    }

    private void handleCaptcha(WebSocketChannel channel, User user, ClientCompleteCaptcha cc) {
        if (!user.isFlaggedForCaptcha()) return;
        if (user.isBanned()) return;

        Unirest
                .post("https://www.google.com/recaptcha/api/siteverify")
                .field("secret", App.getConfig().getString("captcha.secret"))
                .field("response", cc.token)
                //.field("remoteip", "null")
                .asJsonAsync(new Callback<JsonNode>() {
                    @Override
                    public void completed(HttpResponse<JsonNode> response) {
                        JsonNode body = response.getBody();

                        String hostname = App.getConfig().getString("host");

                        boolean success = body.getObject().getBoolean("success") && body.getObject().getString("hostname").equals(hostname);
                        if (success) {
                            user.validateCaptcha();
                        }

                        server.send(channel, new ServerACKCaptchaStatus(success));
                    }

                    @Override
                    public void failed(UnirestException e) {

                    }

                    @Override
                    public void cancelled() {

                    }
                });
    }

    public void handleChatbanState(WebSocketChannel channel, User user, ClientGetChatbanState clientChatbanState) {
        server.send(channel, new ServerReturnChatbanState(user.isPermaChatbanned(), user.getChatbanReason(), user.getChatbanExpiryTime()));
    }

    public void handleClientUserUpdate(WebSocketChannel channel, User user, ClientUpdateUser clientUserUpdate) {
        Map<String,Object> toBroadcast = new HashMap<>();

        String nameColor = clientUserUpdate.updates.get("NameColor");
        if (nameColor != null && !nameColor.trim().isEmpty()) {
            try {
                int t = Integer.parseInt(nameColor);
                if (t >= -2 && t < App.getConfig().getStringList("board.palette").size()) {
                    if (t == -1 && !user.hasPermission("chat.usercolor.rainbow")) {
                        server.send(channel, new ServerACKUserUpdate(false, "Color reserved for staff members", "NameColor", null));
                    }
                    if (t == -2 && !user.hasPermission("chat.usercolor.donator")) {
                        server.send(channel, new ServerACKUserUpdate(false, "Color reserved for donators", "NameColor", null));
                        return;
                    }
                    user.setChatNameColor(t, true);
                    server.send(channel, new ServerACKUserUpdate(true, null, "NameColor", String.valueOf(t)));
                    toBroadcast.put("NameColor", String.valueOf(t));
                } else {
                    server.send(channel, new ServerACKUserUpdate(false, "Color index out of bounds", "NameColor", null));
                }
            } catch (NumberFormatException nfe) {
                server.send(channel, new ServerACKUserUpdate(false, "Invalid color index", "NameColor", null));
            }
        }

        if (!App.getConfig().getBoolean("oauth.snipMode") && toBroadcast.size() > 0) {
            server.broadcast(new ServerNotifyChatUserUpdate(user.getName(), toBroadcast));
        }
    }

    public void handleChatHistory(WebSocketChannel channel, User user, ClientGetChatHistory clientChatHistory) {
        server.send(channel, new ServerReturnChatHistory(App.getDatabase().getlastXMessagesForSocket(100, false, false)));
    }

    public void handleChatMessage(WebSocketChannel channel, User user, ClientSendChatMessage clientChatMessage) {
        int charLimit = Math.min(App.getConfig().getInt("chat.characterLimit"), 2048);
        if (charLimit <= 0) {
            charLimit = 2048;
        }
        Long nowMS = System.currentTimeMillis();
        String message = clientChatMessage.message;
        if (message.contains("\r")) message = message.replaceAll("\r", "");
        if (message.endsWith("\n")) message = message.replaceFirst("\n$", "");
        if (message.length() > charLimit) message = message.substring(0, charLimit);
        if (user == null) { //console
            Integer cmid = App.getDatabase().createChatMessage(0, nowMS / 1000L, message, "");
            server.broadcast(new ServerNotifyChatMessage(new ChatMessage(cmid, "CONSOLE", nowMS / 1000L, message, null, null, 0, null)));
        } else {
            if (!user.canChat()) return;
            if (message.trim().length() == 0) return;
            if (user.isRenameRequested(false)) return;
            int remaining = RateLimitFactory.getTimeRemaining(DBChatMessage.class, String.valueOf(user.getId()));
            if (remaining > 0) {
                server.send(user, new ServerNotifyChatCooldown(remaining, message));
                return;
            }
            try {
                String toSend = message;
                if (App.getConfig().getBoolean("chat.trimInput"))
                    toSend = toSend.trim();
                Faction usersFaction = user.fetchDisplayedFaction();
                String toFilter = "";
                if (App.getConfig().getBoolean("textFilter.enabled")) {
                    TextFilter.FilterResult result = TextFilter.getInstance().filter(toSend);
                    toSend = result.filterHit ? result.filtered : result.original;
                    toFilter = toSend;
                }
                Integer cmid = App.getDatabase().createChatMessage(user.getId(), nowMS / 1000L, message, toFilter);
                server.broadcast(new ServerNotifyChatMessage(new ChatMessage(cmid, user.getName(), nowMS / 1000L, toSend, user.getChatBadges(), user.getChatNameClasses(), user.getChatNameColor(), usersFaction)));
            } catch (UnableToExecuteStatementException utese) {
                utese.printStackTrace();
                System.err.println("Failed to execute the ChatMessage insert statement.");
            }
        }
    }

    public void sendChatban(User user, ServerNotifyChatBan chatban) {
        server.send(user, chatban);
    }

    public void sendChatPurge(User target, User initiator, int amount, String reason) {
        server.broadcast(new ServerNotifyChatPurge(target.getName(), initiator == null ? "CONSOLE" : initiator.getName(), amount, reason));
    }

    public void sendSpecificPurge(User target, User initiator, Integer cmid, String reason) {
        sendSpecificPurge(target, initiator, Collections.singletonList(cmid), reason);
    }

    public void sendSpecificPurge(User target, User initiator, List<Integer> cmids, String reason) {
        server.broadcast(new ServerNotifyChatSpecificPurge(target.getName(), initiator == null ? "CONSOLE" : initiator.getName(), cmids, reason));
    }

    public void updateUserData() {
        server.broadcast(new ServerNotifyUserCount(App.getServer().getNonIdledUsersCount()));
    }

    private void sendCooldownData(WebSocketChannel channel, User user) {
        server.send(channel, new ServerNotifyCooldown(user.getRemainingCooldown()));
    }

    private void sendCooldownData(User user) {
        for (WebSocketChannel ch: user.getConnections()) {
            sendCooldownData(ch, user);
        }
    }

    private void broadcastPixelUpdate(int x, int y, int color) {
        server.broadcast(new ServerNotifyPlace(Collections.singleton(new ServerNotifyPlace.Pixel(x, y, color))));
    }

    public void sendAvailablePixels(WebSocketChannel ch, User user, String cause) {
        server.send(ch, new ServerNotifyPixelsAvailable(user.getAvailablePixels(), cause));
    }
    public void sendAvailablePixels(User user, String cause) {
        for (WebSocketChannel ch : user.getConnections()) {
            sendAvailablePixels(ch, user, cause);
        }
    }

    public void sendPixelCountUpdate(User user) {
        for (WebSocketChannel ch : user.getConnections()) {
            server.send(ch, new ServerNotifyPixelCountUpdate(user));
        }
    }

    private void ackUndo(User user, int x, int y) {
        for (WebSocketChannel ch : user.getConnections()) {
            server.send(ch, new ServerACKUndo(x, y));
        }
    }

    private void ackPlace(User user, int x, int y) {
        for (WebSocketChannel ch : user.getConnections()) {
            server.send(ch, new ServerACKPlace(x, y));
        }
    }

    public int getNumAllCons() {
        return numAllCons;
    }
}
