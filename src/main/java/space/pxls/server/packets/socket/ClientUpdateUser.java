package space.pxls.server.packets.socket;

import java.util.Map;

public class ClientUpdateUser {
    public final Map<String, String> updates;

    public ClientUpdateUser(Map<String, String> updates) {
        this.updates = updates;
    }
}
