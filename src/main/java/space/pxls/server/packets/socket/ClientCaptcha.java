package space.pxls.server.packets.socket;

public class ClientCaptcha {
    public final String token;

    public ClientCaptcha(String token) {
        this.token = token;
    }
}
