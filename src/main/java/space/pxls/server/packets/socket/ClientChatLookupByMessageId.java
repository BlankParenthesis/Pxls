package space.pxls.server.packets.socket;

public class ClientChatLookupByMessageId {
    public final Integer messageId;

    public ClientChatLookupByMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}
