package space.pxls.server.packets.socket;

public class ClientBanMe {
    public final String reason;

    public ClientBanMe(String reason) {
        this.reason = reason;
    }
}
