package space.pxls.server.packets.socket;

public class ServerCooldown {
    public final String type = "cooldown";
    public final Float wait;

    public ServerCooldown(Float wait) {
        this.wait = wait;
    }
}
