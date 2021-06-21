package events;

import entities.media.Media;

/**
 * Event to notify about adding media to export list
 *
 * @author Andrej Benz
 */
public class AddToExport {
  private Media media;

  public AddToExport(Media media) {
    this.media = media;
  }

  public Media getMedia() {
    return this.media;
  }
}
