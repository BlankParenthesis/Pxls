package space.pxls.server.packets.socket.server;

public class NotifyCooldown {
    public final String type = "cooldown";
    public final Float wait;

    public NotifyCooldown(Float wait) {
        this.wait = wait;
    }
}
