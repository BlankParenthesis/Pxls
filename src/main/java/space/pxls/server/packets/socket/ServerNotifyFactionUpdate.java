package space.pxls.server.packets.socket;

import space.pxls.server.packets.http.UserFaction;

public class ServerNotifyFactionUpdate {
    public final String type = "faction_update";
    public final UserFaction faction;

    public ServerNotifyFactionUpdate(UserFaction faction) {
        this.faction = faction;
    }
}
