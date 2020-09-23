package space.pxls.server.packets.socket;

public class ServerReturnChatbanState {
    public final String type = "chat_ban_state";
    public final Boolean permanent;
    public final String reason;
    public final Long expiry;

    public ServerReturnChatbanState(Boolean permanent, String reason, Long expiry) {
        this.permanent = permanent;
        this.reason = reason;
        this.expiry = expiry;
    }
}
