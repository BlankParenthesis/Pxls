package space.pxls.server.packets.socket;

public class ServerRenameSuccess {
    public final String type = "rename_success";
    public final String newName;

    public ServerRenameSuccess(String newName) {
        this.newName = newName;
    }
}
