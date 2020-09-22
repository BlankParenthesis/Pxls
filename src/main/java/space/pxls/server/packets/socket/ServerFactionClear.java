package space.pxls.server.packets.socket;

public class ServerFactionClear {
    public final String type = "faction_clear";
    public final int fid;

    public ServerFactionClear(int fid) {
        this.fid = fid;
    }
}
