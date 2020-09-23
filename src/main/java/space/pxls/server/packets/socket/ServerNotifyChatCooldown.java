package space.pxls.server.packets.socket;

public class ServerNotifyChatCooldown {
    public final String type = "message_cooldown";
    public final Integer diff;
    public final String message;

    public ServerNotifyChatCooldown(Integer diff, String message) {
        this.diff = diff;
        this.message = message;
    }
}
