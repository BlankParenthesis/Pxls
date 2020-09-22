package space.pxls.server.packets.socket;

public class ServerPixels {
    public final String type = "pixels";
    public Integer count;
    public String cause;

    public ServerPixels(Integer count, String cause) {
        this.count = count;
        this.cause = cause;
    }
}
