package space.pxls.server.packets.socket;

public class ServerACKCaptchaStatus {
    public final String type = "captcha_status";
    public final Boolean success;

    public ServerACKCaptchaStatus(Boolean success) {
        this.success = success;
    }
}
