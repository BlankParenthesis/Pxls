package space.pxls.server.packets.socket;

public class ClientPlace {
    public final Integer x;
    public final Integer y;
    public final Integer color;

    public ClientPlace(Integer x, Integer y, Integer color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
