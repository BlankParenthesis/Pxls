package space.pxls.server.packets.socket.server;

public class NotifyFactionClear {
    public final String type = "faction_clear";
    public final int fid;

    public NotifyFactionClear(int fid) {
        this.fid = fid;
    }
}
