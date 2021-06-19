package events;

import entities.media.Media;

public class LoadMetaData {
    private Media media;
    public LoadMetaData(Media media) {
        this.media = media;
    }

    public Media getMedia() {
        return media;
    }
}
