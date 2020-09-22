package space.pxls.server.packets.chat;

import java.util.Map;

public class ClientUserUpdate {
    public final Map<String, String> updates;

    public ClientUserUpdate(Map<String, String> updates) {
        this.updates = updates;
    }
}
