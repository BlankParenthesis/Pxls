package space.pxls.server.packets.socket.server;

public class NotifyChatBan {
    public final String type = "chat_ban";
    public final Boolean permanent;
    public final String reason;
    public final Long expiry;

    public NotifyChatBan(Boolean permanent, String reason, Long expiry) {
        this.permanent = permanent;
        this.reason = reason;
        this.expiry = expiry;
    }
}
