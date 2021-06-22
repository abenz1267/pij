package events;

import entities.media.Media;

/**
 * Event to notify about adding media to custom diashow
 *
 * @author Christian Paulsen
 */
public class AddToDiashow {
  private Media media;

  /**
   * Add the media, that will be added to a custom diashow
   * @param media the media
   */
  public AddToDiashow(Media media) {
    this.media = media;
  }

  /**
   * Returns the media for this event
   * @return the media document for this event
   */
  public Media getMedia() {
    return this.media;
  }
}
