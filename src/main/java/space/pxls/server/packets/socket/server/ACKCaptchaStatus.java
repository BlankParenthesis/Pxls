package space.pxls.server.packets.socket.server;

public class ACKCaptchaStatus {
    public final String type = "captcha_status";
    public final Boolean success;

    public ACKCaptchaStatus(Boolean success) {
        this.success = success;
    }
}
