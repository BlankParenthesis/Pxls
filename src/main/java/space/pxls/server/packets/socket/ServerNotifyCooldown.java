package space.pxls.server.packets.socket;

public class ServerNotifyCooldown {
    public final String type = "cooldown";
    public final Float wait;

    public ServerNotifyCooldown(Float wait) {
        this.wait = wait;
    }
}
