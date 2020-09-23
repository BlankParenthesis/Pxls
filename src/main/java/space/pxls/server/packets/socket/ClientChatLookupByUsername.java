package space.pxls.server.packets.socket;

public class ClientChatLookupByUsername {
    public final String username;

    public ClientChatLookupByUsername(String username) {
        this.username = username;
    }
}
