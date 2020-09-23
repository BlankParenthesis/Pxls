package space.pxls.server.packets.socket.server;

import space.pxls.server.packets.http.server.UserFaction;

public class NotifyFactionUpdate {
    public final String type = "faction_update";
    public final UserFaction faction;

    public NotifyFactionUpdate(UserFaction faction) {
        this.faction = faction;
    }
}
