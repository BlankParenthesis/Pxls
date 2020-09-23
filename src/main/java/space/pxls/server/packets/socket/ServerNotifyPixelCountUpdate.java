package space.pxls.server.packets.socket;

import space.pxls.user.User;

public class ServerNotifyPixelCountUpdate {
	public final String type = "pixel_counts";
	public final Integer pixelCount;
	public final Integer pixelCountAllTime;

	public ServerNotifyPixelCountUpdate(Integer pixelCount, Integer pixelCountAllTime) {
		this.pixelCount = pixelCount;
		this.pixelCountAllTime = pixelCountAllTime;
	}

	public ServerNotifyPixelCountUpdate(User user) {
		this.pixelCount = user.getPixelCount();
		this.pixelCountAllTime = user.getAllTimePixelCount();
	}
}
