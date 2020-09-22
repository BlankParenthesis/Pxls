package space.pxls.server.packets.socket;

public class ServerUsers {
    public final String type = "users";
    public final Integer count;

    public ServerUsers(Integer count) {
        this.count = count;
    }
}
