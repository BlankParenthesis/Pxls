package space.pxls.server.packets.socket;

import space.pxls.user.Role;

import java.util.List;

public class ServerNotifyUserInfo {
    public final String type = "userinfo";
    public final String username;
    public final String login;
    public final List<Role> roles;
    public final int pixelCount;
    public final int pixelCountAllTime;
    public final Boolean banned;
    public final Long banExpiry;
    public final String banReason;
    public final String method;
    public final Boolean cdOverride;
    public final Boolean chatBanned;
    public final String chatbanReason;
    public final Boolean chatbanIsPerma;
    public final Long chatbanExpiry;
    public final Boolean renameRequested;
    public final String discordName;
    public final Number chatNameColor;

    public ServerNotifyUserInfo(String username, String login, List<Role> roles, int pixelCount, int pixelCountAllTime,
                                Boolean banned, Long banExpiry, String banReason, String method, Boolean cdOverride,
                                Boolean chatBanned, String chatbanReason, Boolean chatbanIsPerma, Long chatbanExpiry,
                                Boolean renameRequested, String discordName, Number chatNameColor) {
        this.username = username;
        this.login = login;
        this.roles = roles;
        this.pixelCount = pixelCount;
        this.pixelCountAllTime = pixelCountAllTime;
        this.banned = banned;
        this.banExpiry = banExpiry;
        this.banReason = banReason;
        this.method = method;
        this.cdOverride = cdOverride;
        this.chatBanned = chatBanned;
        this.chatbanReason = chatbanReason;
        this.chatbanIsPerma = chatbanIsPerma;
        this.chatbanExpiry = chatbanExpiry;
        this.renameRequested = renameRequested;
        this.discordName = discordName;
        this.chatNameColor = chatNameColor;
    }
}
