package space.pxls.server.packets.socket;

import space.pxls.user.ChatMessage;

import java.util.List;

public class ServerChatHistory {
    public final String type = "chat_history";
    public final List<ChatMessage> messages;

    public ServerChatHistory(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
