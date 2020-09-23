package space.pxls.server.packets.socket.server;

public class ACKUserUpdate {
    public final String type = "ack_user_update";
    public final Boolean success;
    public final String message;
    public final String updateType;
    public final String updateValue;

    public ACKUserUpdate(Boolean success, String message, String updateType, String updateValue) {
        this.success = success;
        this.message = message;
        this.updateType = updateType;
        this.updateValue = updateValue;
    }
}