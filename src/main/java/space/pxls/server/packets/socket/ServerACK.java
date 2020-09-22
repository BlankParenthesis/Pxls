package space.pxls.server.packets.socket;

public class ServerACK {
    public final String type = "ACK";
    public final String ackFor;
    public final Integer x;
    public final Integer y;

    public ServerACK(String ackFor, Integer x, Integer y) {
        this.ackFor = ackFor;
        this.x = x;
        this.y = y;
    }
}
