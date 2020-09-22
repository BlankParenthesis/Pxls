package space.pxls.server.packets.socket;

import space.pxls.data.DBNotification;

public class ServerNotification {
    public final String type = "notification";
    public final DBNotification notification;

    public ServerNotification(DBNotification notification) {
        this.notification = notification;
    }
}
