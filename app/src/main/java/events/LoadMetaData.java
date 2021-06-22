package events;

import entities.media.Media;

/**
 * Event to load MetaData.
 *
 * @author Timm Lohmann
 * @author Joey Wille
 * @author Phillip Knutzen
 */

public class LoadMetaData {
  private Media media;

  public LoadMetaData(Media media) {
    this.media = media;
  }

  public Media getMedia() {
    return media;
  }
}
