package space.pxls.server.packets.chat;

public class ClientChatLookup {
    public final String arg;
    public final String mode;

    public ClientChatLookup(String arg, String mode) {
        this.arg = arg;
        this.mode = mode;
    }
}
