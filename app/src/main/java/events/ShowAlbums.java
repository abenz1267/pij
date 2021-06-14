package events;

import entities.album.Album;

import java.util.List;

public class ShowAlbums {
    private List<Album> albumList;

    public ShowAlbums(List<Album> album) {
        this.albumList = album;
    }

    public List<Album> getAlbums() {
        return this.albumList;
    }
}
