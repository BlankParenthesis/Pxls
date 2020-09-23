package space.pxls.server.packets.socket;

public class ClientSendChatMessage {
    public final String message;

    public ClientSendChatMessage(String message) {
        this.message = message;
    }
}
