package space.pxls.server.packets.http;

import space.pxls.auth.AuthService;

import java.util.List;
import java.util.Map;

public class CanvasInfo {
    public final String canvasCode;
    public final Integer width;
    public final Integer height;
    public final List<String> palette;
    public final String captchaKey;
    public final Integer heatmapCooldown;
    public final Integer maxStacked;
    public final Map<String, AuthService> authServices;
    public final Boolean registrationEnabled;
    public final Boolean chatRespectsCanvasBan;
    public final Integer chatCharacterLimit;
    public final Boolean snipMode;

    public CanvasInfo(String canvasCode, Integer width, Integer height, List<String> palette, String captchaKey, Integer heatmapCooldown, Integer maxStacked, Map<String, AuthService> authServices, Boolean registrationEnabled, Integer chatCharacterLimit, boolean chatRespectsCanvasBan, boolean snipMode) {
        this.canvasCode = canvasCode;
        this.width = width;
        this.height = height;
        this.palette = palette;
        this.captchaKey = captchaKey;
        this.heatmapCooldown = heatmapCooldown;
        this.maxStacked = maxStacked;
        this.authServices = authServices;
        this.registrationEnabled = registrationEnabled;
        this.chatCharacterLimit = chatCharacterLimit;
        this.chatRespectsCanvasBan = chatRespectsCanvasBan;
        this.snipMode = snipMode;
    }
}
