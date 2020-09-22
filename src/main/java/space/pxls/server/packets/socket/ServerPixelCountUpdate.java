package space.pxls.server.packets.socket;

import space.pxls.user.User;

public class ServerPixelCountUpdate {
	public final String type = "pixelCounts";
	public final Integer pixelCount;
	public final Integer pixelCountAllTime;

	public ServerPixelCountUpdate(Integer pixelCount, Integer pixelCountAllTime) {
		this.pixelCount = pixelCount;
		this.pixelCountAllTime = pixelCountAllTime;
	}

	public ServerPixelCountUpdate(User user) {
		this.pixelCount = user.getPixelCount();
		this.pixelCountAllTime = user.getAllTimePixelCount();
	}
}
