package space.pxls.server.packets.socket.client;

public class ChatLookupByMessageId {
    public final Integer messageId;

    public ChatLookupByMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}
