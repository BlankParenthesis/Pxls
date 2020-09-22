package space.pxls.server.packets.socket;

public class ClientAdminMessage {
    public final String username;
    public final String message;

    public ClientAdminMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }
}
