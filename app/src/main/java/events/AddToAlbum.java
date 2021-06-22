package events;

import entities.media.Media;

/**
 * Event to notify about adding media to album list
 *
 * @author Timm Lohmann
 */
public class AddToAlbum {
  private Media media;

  public AddToAlbum(Media media) {
    this.media = media;
  }

  public Media getMedia() {
    return this.media;
  }
}
