package space.pxls.server.packets.socket;

public class ServerACKUndo {
    public final String type = "ack_undo";
    public final Integer x;
    public final Integer y;

    public ServerACKUndo(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
}
