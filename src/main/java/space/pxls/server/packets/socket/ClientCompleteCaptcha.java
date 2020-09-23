package space.pxls.server.packets.socket;

public class ClientCompleteCaptcha {
    public final String token;

    public ClientCompleteCaptcha(String token) {
        this.token = token;
    }
}
