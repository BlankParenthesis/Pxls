package space.pxls.server.packets.socket;

public class ServerNotifyCanUndo {
    public final String type = "can_undo";
    public final Long time;
    
    public ServerNotifyCanUndo(Long time) {
        this.time = time;
    }
}
