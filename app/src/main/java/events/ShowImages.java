package events;

import entities.album.Album;
import entities.media.Media;
import java.util.List;

/**
 * Event to show images.
 *
 * @author Andrej Benz
 */
public class ShowImages {
  private List<Media> media;
  private Album album;

  public ShowImages(List<Media> media) {
    this.media = media;
  }

  public ShowImages(List<Media> media, Album album) {
    this.media = media;
    this.album = album;
  }

  public List<Media> getMedia() {
    return this.media;
  }

  public Album getAlbum() {
    return album;
  }
}
