package space.pxls.server.packets.socket.server;

import space.pxls.data.DBNotification;

public class Notification {
    public final String type = "notification";
    public final DBNotification notification;

    public Notification(DBNotification notification) {
        this.notification = notification;
    }
}
