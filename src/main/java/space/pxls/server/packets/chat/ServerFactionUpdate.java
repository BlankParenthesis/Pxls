package space.pxls.server.packets.chat;

import space.pxls.server.packets.http.UserFaction;

public class ServerFactionUpdate {
    public final String type = "faction_update";
    public final UserFaction faction;

    public ServerFactionUpdate(UserFaction faction) {
        this.faction = faction;
    }
}
