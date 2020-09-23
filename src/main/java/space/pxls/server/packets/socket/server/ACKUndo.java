package space.pxls.server.packets.socket.server;

public class ACKUndo {
    public final String type = "ack_undo";
    public final Integer x;
    public final Integer y;

    public ACKUndo(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
}
