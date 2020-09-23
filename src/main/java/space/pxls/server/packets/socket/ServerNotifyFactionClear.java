package space.pxls.server.packets.socket;

public class ServerNotifyFactionClear {
    public final String type = "faction_clear";
    public final int fid;

    public ServerNotifyFactionClear(int fid) {
        this.fid = fid;
    }
}
