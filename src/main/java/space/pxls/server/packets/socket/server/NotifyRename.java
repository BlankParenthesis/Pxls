package space.pxls.server.packets.socket.server;

public class NotifyRename {
    public final String type = "rename_success";
    public final String newName;

    public NotifyRename(String newName) {
        this.newName = newName;
    }
}
