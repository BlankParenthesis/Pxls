package space.pxls.server.packets.socket.client;

import java.util.Map;

public class UpdateUser {
    public final Map<String, String> updates;

    public UpdateUser(Map<String, String> updates) {
        this.updates = updates;
    }
}
