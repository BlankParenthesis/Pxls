package space.pxls.server.packets.socket;

import space.pxls.data.DBChatMessage;
import space.pxls.data.DBExtendedChatban;
import space.pxls.data.DBUser;

import java.util.List;

public class ServerChatLookup {
    public final String type = "chat_lookup";
    public final DBUser target;
    public final List<DBChatMessage> history;
    public final List<DBExtendedChatban> chatbans;

    public ServerChatLookup(DBUser target, List<DBChatMessage> history, List<DBExtendedChatban> chatbans) {
        this.target = target;
        this.history = history;
        this.chatbans = chatbans;
    }
}
