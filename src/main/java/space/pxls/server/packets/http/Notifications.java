package space.pxls.server.packets.http;

import java.util.List;

public class Notifications {
    public final List<Notification> notifications;

    public Notifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
