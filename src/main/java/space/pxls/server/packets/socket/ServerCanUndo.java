package space.pxls.server.packets.socket;

public class ServerCanUndo {
    public final String type = "can_undo";
    public final Long time;
    
    public ServerCanUndo(Long time) {
        this.time = time;
    }
}
