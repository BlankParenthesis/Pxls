package space.pxls.server.packets.socket;

public class ServerNotifyRenameRequested {
    public final String type = "rename";
    public final Boolean requested;

    public ServerNotifyRenameRequested(Boolean requested) {
        this.requested = requested;
    }
}
