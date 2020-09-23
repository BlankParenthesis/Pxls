package space.pxls.server.packets.socket.server;

import java.util.Collection;

public class NotifyPlace {
    public final String type = "pixel";
    public final Collection<Pixel> pixels;

    public NotifyPlace(Collection<Pixel> pixels) {
        this.pixels = pixels;
    }

    public static class Pixel {
        public final Integer x;
        public final Integer y;
        public final Integer color;

        public Pixel(Integer x, Integer y, Integer color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }
}
