package space.pxls.server.packets.socket.server;

public class ReturnChatbanState {
    public final String type = "chat_ban_state";
    public final Boolean permanent;
    public final String reason;
    public final Long expiry;

    public ReturnChatbanState(Boolean permanent, String reason, Long expiry) {
        this.permanent = permanent;
        this.reason = reason;
        this.expiry = expiry;
    }
}
