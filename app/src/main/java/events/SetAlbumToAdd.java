package events;

import entities.album.Album;


/**
 * Event to get album entity to add.
 *
 * @author Timm Lohmann
 * @author Phillip Knutzen
 * @author Joey Wille
 */

public class SetAlbumToAdd {
    private Album album;

    public SetAlbumToAdd(Album album) {
        this.album = album;
    }

    public Album getAlbum() {
        return album;
    }
}
