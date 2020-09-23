package space.pxls.server.packets.socket;

import space.pxls.user.ChatMessage;

import java.util.List;

public class ServerReturnChatHistory {
    public final String type = "chat_history";
    public final List<ChatMessage> messages;

    public ServerReturnChatHistory(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
