package space.pxls.server.packets.socket.server;

public class NotifyChatCooldown {
    public final String type = "message_cooldown";
    public final Integer diff;
    public final String message;

    public NotifyChatCooldown(Integer diff, String message) {
        this.diff = diff;
        this.message = message;
    }
}
