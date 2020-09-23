package space.pxls.server.packets.socket.server;

import space.pxls.user.ChatMessage;

public class NotifyChatMessage {
    public final String type = "chat_message";
    public final ChatMessage message;

    public NotifyChatMessage(ChatMessage message) {
        this.message = message;
    }
}
