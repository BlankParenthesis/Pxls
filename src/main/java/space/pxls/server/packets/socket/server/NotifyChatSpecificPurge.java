package space.pxls.server.packets.socket.server;

import java.util.List;

public class NotifyChatSpecificPurge {
    public final String type = "chat_purge_specific";
    public final String target;
    public final String initiator;
    public final List<Integer> IDs;
    public final String reason;

    public NotifyChatSpecificPurge(String target, String initiator, List<Integer> IDs, String reason) {
        this.target = target;
        this.initiator = initiator;
        this.IDs = IDs;
        this.reason = reason;
    }
}
