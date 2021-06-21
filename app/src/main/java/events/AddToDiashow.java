package events;

import entities.media.Media;

public class AddToDiashow {
  private Media media;

  public AddToDiashow(Media media) {
    this.media = media;
  }

  public Media getMedia() {
    return this.media;
  }
}
