package space.pxls.server.packets.socket.server;

public class NotifyRenameRequested {
    public final String type = "rename";
    public final Boolean requested;

    public NotifyRenameRequested(Boolean requested) {
        this.requested = requested;
    }
}
