package events;

import entities.album.Album;

public class SetAlbumToAdd {
    private Album album;

    public SetAlbumToAdd(Album album) {
        this.album = album;
    }

    public Album getAlbum() {
        return album;
    }
}
