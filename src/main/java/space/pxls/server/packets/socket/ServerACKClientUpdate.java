package space.pxls.server.packets.socket;

public class ServerACKClientUpdate {
    public final String type = "ack_client_update";
    public final Boolean success;
    public final String message;
    public final String updateType;
    public final String updateValue;

    public ServerACKClientUpdate(Boolean success, String message, String updateType, String updateValue) {
        this.success = success;
        this.message = message;
        this.updateType = updateType;
        this.updateValue = updateValue;
    }
}