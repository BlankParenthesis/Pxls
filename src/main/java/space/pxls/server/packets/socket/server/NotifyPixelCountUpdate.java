package space.pxls.server.packets.socket.server;

import space.pxls.user.User;

public class NotifyPixelCountUpdate {
	public final String type = "pixel_counts";
	public final Integer pixelCount;
	public final Integer pixelCountAllTime;

	public NotifyPixelCountUpdate(Integer pixelCount, Integer pixelCountAllTime) {
		this.pixelCount = pixelCount;
		this.pixelCountAllTime = pixelCountAllTime;
	}

	public NotifyPixelCountUpdate(User user) {
		this.pixelCount = user.getPixelCount();
		this.pixelCountAllTime = user.getAllTimePixelCount();
	}
}
