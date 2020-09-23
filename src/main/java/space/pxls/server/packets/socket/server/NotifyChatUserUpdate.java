package space.pxls.server.packets.socket.server;

import java.util.Map;

public class NotifyChatUserUpdate {
    public final String type = "chat_user_update";
    public final String who;
    public final Map<String, Object> updates;

    public NotifyChatUserUpdate(String who, Map<String, Object> updates) {
        this.who = who;
        this.updates = updates;
    }
}
