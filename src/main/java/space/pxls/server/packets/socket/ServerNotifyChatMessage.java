package space.pxls.server.packets.socket;

import space.pxls.user.ChatMessage;

public class ServerNotifyChatMessage {
    public final String type = "chat_message";
    public final ChatMessage message;

    public ServerNotifyChatMessage(ChatMessage message) {
        this.message = message;
    }
}
