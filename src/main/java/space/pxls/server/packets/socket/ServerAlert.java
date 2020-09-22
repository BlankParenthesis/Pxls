package space.pxls.server.packets.socket;

public class ServerAlert {
    public final String type = "alert";
    public final String message;
    public final String sender;

    public ServerAlert(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }
}
