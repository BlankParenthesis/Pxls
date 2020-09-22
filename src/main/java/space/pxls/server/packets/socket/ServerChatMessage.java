package space.pxls.server.packets.socket;

import space.pxls.user.ChatMessage;

public class ServerChatMessage {
    public final String type = "chat_message";
    public final ChatMessage message;

    public ServerChatMessage(ChatMessage message) {
        this.message = message;
    }
}
