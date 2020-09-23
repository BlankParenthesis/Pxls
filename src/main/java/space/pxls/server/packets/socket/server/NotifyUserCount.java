package space.pxls.server.packets.socket.server;

public class NotifyUserCount {
    public final String type = "users";
    public final Integer count;

    public NotifyUserCount(Integer count) {
        this.count = count;
    }
}
