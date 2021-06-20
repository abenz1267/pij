package events;

import entities.media.Media;
import java.util.List;

public class PlayDiashow {
    private final List<Media> media;

    public PlayDiashow(List<Media> media) {
        this.media = media;
    }

    public List<Media> getMedia() {
        return this.media;
    }
}


