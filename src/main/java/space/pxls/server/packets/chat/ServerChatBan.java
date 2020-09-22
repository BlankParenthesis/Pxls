package space.pxls.server.packets.chat;

public class ServerChatBan {
    public final String type = "chat_ban";
    public final Boolean permanent;
    public final String reason;
    public final Long expiry;

    public ServerChatBan(Boolean permanent, String reason, Long expiry) {
        this.permanent = permanent;
        this.reason = reason;
        this.expiry = expiry;
    }
}
