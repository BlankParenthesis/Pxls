package space.pxls.server.packets.socket;

public class ClientSendAdminMessage {
    public final String username;
    public final String message;

    public ClientSendAdminMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }
}
