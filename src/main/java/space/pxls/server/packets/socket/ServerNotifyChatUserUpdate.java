package space.pxls.server.packets.socket;

import java.util.Map;

public class ServerNotifyChatUserUpdate {
    public final String type = "chat_user_update";
    public final String who;
    public final Map<String, Object> updates;

    public ServerNotifyChatUserUpdate(String who, Map<String, Object> updates) {
        this.who = who;
        this.updates = updates;
    }
}
