package space.pxls.server.packets.socket;

public class ClientPlace {
    public final String type;
    public final Integer x;
    public final Integer y;
    public final Integer color;

    public ClientPlace(String type, Integer x, Integer y, Integer color) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
