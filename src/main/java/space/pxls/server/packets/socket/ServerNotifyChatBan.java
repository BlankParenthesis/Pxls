package space.pxls.server.packets.socket;

public class ServerNotifyChatBan {
    public final String type = "chat_ban";
    public final Boolean permanent;
    public final String reason;
    public final Long expiry;

    public ServerNotifyChatBan(Boolean permanent, String reason, Long expiry) {
        this.permanent = permanent;
        this.reason = reason;
        this.expiry = expiry;
    }
}
