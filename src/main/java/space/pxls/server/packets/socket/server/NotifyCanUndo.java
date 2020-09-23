package space.pxls.server.packets.socket.server;

public class NotifyCanUndo {
    public final String type = "can_undo";
    public final Long time;
    
    public NotifyCanUndo(Long time) {
        this.time = time;
    }
}
