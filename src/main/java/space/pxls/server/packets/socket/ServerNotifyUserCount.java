package space.pxls.server.packets.socket;

public class ServerNotifyUserCount {
    public final String type = "users";
    public final Integer count;

    public ServerNotifyUserCount(Integer count) {
        this.count = count;
    }
}
