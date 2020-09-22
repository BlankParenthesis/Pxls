package space.pxls.server.packets.http;

public class Notification {
    public final Number id;
    public final String title;
    public final String body;
    public final Long time;
    public final Long expiry;
    public final String who;

    public Notification(Number id, String title, String body, Long time, Long expiry, String who) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.time = time;
        this.expiry = expiry;
        this.who = who;
    }
}
