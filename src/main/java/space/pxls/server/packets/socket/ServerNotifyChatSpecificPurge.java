package space.pxls.server.packets.socket;

import java.util.List;

public class ServerNotifyChatSpecificPurge {
    public final String type = "chat_purge_specific";
    public final String target;
    public final String initiator;
    public final List<Integer> IDs;
    public final String reason;

    public ServerNotifyChatSpecificPurge(String target, String initiator, List<Integer> IDs, String reason) {
        this.target = target;
        this.initiator = initiator;
        this.IDs = IDs;
        this.reason = reason;
    }
}
