package space.pxls.server.packets.http;

import space.pxls.data.DBFaction;
import space.pxls.data.DBFactionSearch;
import space.pxls.user.Faction;

public class UserFaction {
    public final int id;
    public final int color;
    public final String name;
    public final String tag;
    public final String owner;
    public final String canvasCode;
    public final long creation_ms;
    public final Integer memberCount;
    public final Boolean userJoined;

    public UserFaction(DBFaction faction) {
        this(new Faction(faction), null, null);
    }

    public UserFaction(DBFactionSearch dbFactionSearch) {
        this(
            new Faction(dbFactionSearch), 
            dbFactionSearch.memberCount, 
            dbFactionSearch.userJoined
        );
    }

    public UserFaction(Faction faction) {
        this(faction, null, null);
    }

    private UserFaction(Faction faction, Integer memberCount, Boolean userJoined) {
        this.id = faction.getId();
        this.color = faction.getColor();
        this.name = faction.getName();
        this.tag = faction.getTag();
        this.owner = faction.fetchOwner().getName();
        this.creation_ms = faction.getCreated().toInstant().toEpochMilli();
        this.canvasCode = faction.getCanvasCode();
        this.memberCount = memberCount;
        this.userJoined = userJoined;
    }
}
