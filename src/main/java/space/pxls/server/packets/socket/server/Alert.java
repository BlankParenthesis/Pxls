package space.pxls.server.packets.socket.server;

public class Alert {
    public final String type = "alert";
    public final String message;
    public final String sender;

    public Alert(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }
}
