package space.pxls.server.packets.chat;

import java.util.Map;

public class ServerChatUserUpdate {
    public final String type = "chat_user_update";
    public final String who;
    public final Map<String, Object> updates;

    public ServerChatUserUpdate(String who, Map<String, Object> updates) {
        this.who = who;
        this.updates = updates;
    }
}
