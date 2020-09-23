package space.pxls.server.packets.socket.server;

public class ACKPlace {
    public final String type = "ack_place";
    public final Integer x;
    public final Integer y;

    public ACKPlace(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
}
