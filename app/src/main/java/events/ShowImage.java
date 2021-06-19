package events;

import entities.media.Media;

public class ShowImage {
    private Media media;
    public ShowImage(Media media) {
        this.media = media;
    }

    public Media getMedia() {
        return media;
    }
}
