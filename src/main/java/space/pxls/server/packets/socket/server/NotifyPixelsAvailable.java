package space.pxls.server.packets.socket.server;

public class NotifyPixelsAvailable {
    public final String type = "pixels";
    public Integer count;
    public String cause;

    public NotifyPixelsAvailable(Integer count, String cause) {
        this.count = count;
        this.cause = cause;
    }
}
