package space.pxls.server.packets.socket.client;

public class SendAdminMessage {
    public final String username;
    public final String message;

    public SendAdminMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }
}
