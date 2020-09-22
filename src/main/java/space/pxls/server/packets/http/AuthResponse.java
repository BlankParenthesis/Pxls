package space.pxls.server.packets.http;

public class AuthResponse {
    public final String token;
    public final Boolean signup;

    public AuthResponse(String token, Boolean signup) {
        this.token = token;
        this.signup = signup;
    }
}
