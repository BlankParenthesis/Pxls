package space.pxls.server.packets.socket;

public class ServerRename {
    public final String type = "rename";
    public final Boolean requested;

    public ServerRename(Boolean requested) {
        this.requested = requested;
    }
}
