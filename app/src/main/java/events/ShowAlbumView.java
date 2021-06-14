package events;

import entities.album.Album;
import java.util.List;

public class ShowAlbumView {
    private List<Album> album;

    public void ShowAlbums(List<Album> album) {
        this.album = album;
    }

    public List<Album> getAlbum() {
        return this.album;
    }
}
