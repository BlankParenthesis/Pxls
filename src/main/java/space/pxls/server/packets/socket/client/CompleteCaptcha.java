package space.pxls.server.packets.socket.client;

public class CompleteCaptcha {
    public final String token;

    public CompleteCaptcha(String token) {
        this.token = token;
    }
}
