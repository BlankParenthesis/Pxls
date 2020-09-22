package space.pxls.server.packets.socket;

public class ServerCaptchaStatus {
    public final String type = "captcha_status";
    public final Boolean success;

    public ServerCaptchaStatus(Boolean success) {
        this.success = success;
    }
}
