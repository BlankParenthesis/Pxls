package space.pxls.server.packets.chat;

public class ServerChatbanState {
    public final String type = "chat_ban_state";
    public final Boolean permanent;
    public final String reason;
    public final Long expiry;

    public ServerChatbanState(Boolean permanent, String reason, Long expiry) {
        this.permanent = permanent;
        this.reason = reason;
        this.expiry = expiry;
    }
}
