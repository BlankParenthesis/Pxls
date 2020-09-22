package space.pxls.server.packets.socket;

public class ServerReceivedReport {
    public final String type = "received_report";
    public final Integer report_id;
    public final Type report_type;

    public ServerReceivedReport(Integer report_id, Type report_type) {
        this.report_id = report_id;
        this.report_type = report_type;
    }

    public enum Type {
        CHAT("CHAT"),
        CANVAS("CANVAS");

        private final String value;
        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
}
