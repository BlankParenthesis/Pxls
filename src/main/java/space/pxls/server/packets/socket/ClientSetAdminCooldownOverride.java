package space.pxls.server.packets.socket;

public class ClientSetAdminCooldownOverride {
    public final Boolean override;

    public ClientSetAdminCooldownOverride(Boolean override) {
        this.override = override;
    }
}
