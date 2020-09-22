package space.pxls.server.packets.chat;

public class ServerChatMessage {
    public final String type = "chat_message";
    public final ChatMessage message;

    public ServerChatMessage(ChatMessage message) {
        this.message = message;
    }
}
