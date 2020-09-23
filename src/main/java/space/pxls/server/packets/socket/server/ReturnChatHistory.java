package space.pxls.server.packets.socket.server;

import space.pxls.user.ChatMessage;

import java.util.List;

public class ReturnChatHistory {
    public final String type = "chat_history";
    public final List<ChatMessage> messages;

    public ReturnChatHistory(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
