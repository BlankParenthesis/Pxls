package space.pxls.server.packets.socket;

public class ServerNotifyRename {
    public final String type = "rename_success";
    public final String newName;

    public ServerNotifyRename(String newName) {
        this.newName = newName;
    }
}
