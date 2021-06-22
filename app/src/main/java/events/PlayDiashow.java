package events;

import entities.media.Media;
import java.util.List;

/**
 * Event to notify about the start of a diashow with the given media list
 *
 * @author Christian Paulsen
 */
public class PlayDiashow {
  private List<Media> media;

  /**
   * Play Diashow calls an event to start a diashow
   * @param media the documents that will be displayed in the diashow
   */
  public PlayDiashow(List<Media> media) {
    this.media = media;
  }

  /**
   * Returns the media for the diashow
   * @return list of media elements
   */
  public List<Media> getMedia() {
    return this.media;
  }
}
