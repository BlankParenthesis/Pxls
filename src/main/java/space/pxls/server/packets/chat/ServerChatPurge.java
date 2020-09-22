package space.pxls.server.packets.chat;

import space.pxls.App;

public class ServerChatPurge {
    public final String type = "chat_purge";
    public final String target;
    public final String initiator;
    public final Integer amount;
    public final String reason;

    public ServerChatPurge(String target, String initiator, Integer amount, String reason) {
        this.target = App.getConfig().getBoolean("oauth.snipMode") ? "-snip-" : target;
        this.initiator = initiator;
        this.amount = amount;
        this.reason = reason;
    }
}
