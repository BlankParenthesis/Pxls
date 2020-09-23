package space.pxls.server.packets.socket;

public class ServerReceivedReport {
    public final String type = "received_report";
    public final Integer reportId;
    public final Type reportType;

    public ServerReceivedReport(Integer reportId, Type reportType) {
        this.reportId = reportId;
        this.reportType = reportType;
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
