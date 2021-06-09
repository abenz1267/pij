package events;

import entities.media.Media;
import java.util.List;

public class ShowImages {
  private List<Media> media;

  public ShowImages(List<Media> media) {
    this.media = media;
  }

  public List<Media> getMedia() {
    return this.media;
  }
}
