package space.pxls.server.packets.socket;

public class ServerACKPlace {
    public final String type = "ack_place";
    public final Integer x;
    public final Integer y;

    public ServerACKPlace(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
}
