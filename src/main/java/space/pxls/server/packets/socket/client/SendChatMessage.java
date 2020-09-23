package space.pxls.server.packets.socket.client;

public class SendChatMessage {
    public final String message;

    public SendChatMessage(String message) {
        this.message = message;
    }
}
