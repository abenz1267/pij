package events;

import entities.media.Media;

public class AddToAlbum {
    private Media media;

    public AddToAlbum(Media media) {
        this.media = media;
    }

    public Media getMedia() {
        return this.media;
    }
}
