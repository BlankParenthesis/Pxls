package space.pxls.server.packets.http;

public class Error {
    public final String error;
    public final String message;

    public Error(String error, String message) {
        this.error = error;
        this.message = message;
    }
}
