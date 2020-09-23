package space.pxls.server.packets.http.server;

public class WhoAmI {
    public final String username;
    public final Integer id;

    public WhoAmI(String username, Integer id) {
        this.username = username;
        this.id = id;
    }
}
