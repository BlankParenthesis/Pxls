package space.pxls.server.packets.http.server;

import space.pxls.App;
import space.pxls.data.DBPixelPlacementUser;

public class Lookup {
    public final int id;
    public final int x;
    public final int y;
    public final int pixelCount;
    public final int pixelCountAlltime;
    public final long time;
    public final String username;
    public final String discordName;
    public final String faction;

    protected Lookup(int id, int x, int y, int pixel_count, int pixel_count_alltime, long time, String username, String discordName, String faction) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.pixelCount = pixel_count;
        this.pixelCountAlltime = pixel_count_alltime;
        this.time = time;
        this.username = username;
        this.discordName = discordName;
        this.faction = faction;
    }

    public static Lookup fromDB(DBPixelPlacementUser pixelPlacementUser) {
        if (pixelPlacementUser == null) return null;
        boolean isSnip = App.getConfig().getBoolean("oauth.snipMode");
        return new Lookup(
            pixelPlacementUser.id, 
            pixelPlacementUser.x, 
            pixelPlacementUser.y, 
            isSnip ? 0 : pixelPlacementUser.pixel_count, 
            isSnip ? 0 : pixelPlacementUser.pixel_count_alltime, 
            pixelPlacementUser.time, 
            isSnip ? "-snip-" : pixelPlacementUser.username, 
            // NOTE ([  ]): The following line will leak information in snip mode about whether or not the user has discord linked. 
            //              It's not the place of this changeset to deal with that, but I'd rather note that than let it get lost.
            isSnip ? (pixelPlacementUser.discordName != null ? "-snip-" : null) : pixelPlacementUser.discordName,  // if we're in snip mode, we want to filter the name, otherwise we'll just accept whatever was thrown at us. original serialization utilized nulls.
            pixelPlacementUser.faction
        );
    }
}
