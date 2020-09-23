package space.pxls.server.packets.socket;

public class ServerNotifyPixelsAvailable {
    public final String type = "pixels";
    public Integer count;
    public String cause;

    public ServerNotifyPixelsAvailable(Integer count, String cause) {
        this.count = count;
        this.cause = cause;
    }
}
