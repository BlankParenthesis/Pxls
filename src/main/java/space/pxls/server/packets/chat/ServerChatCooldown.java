package space.pxls.server.packets.chat;

public class ServerChatCooldown {
    public final String type = "message_cooldown";
    public final Integer diff;
    public final String message;

    public ServerChatCooldown(Integer diff, String message) {
        this.diff = diff;
        this.message = message;
    }
}
