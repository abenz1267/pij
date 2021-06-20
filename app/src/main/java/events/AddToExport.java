package events;

import entities.media.Media;

public class AddToExport {
  private Media media;

  public AddToExport(Media media) {
    this.media = media;
  }

  public Media getMedia() {
    return this.media;
  }
}
