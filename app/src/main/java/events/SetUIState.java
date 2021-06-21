package events;

/**
 * Event to notify the UI about general state.
 *
 * @author Andrej Benz
 * @author Timm Lohmann
 * @author Joey Wille
 * @author Phillip Knutzen
 * @author Christian Paulsen
 * @author Kelvin Leclaire
 * @author Huilun Chen
 */
import entities.album.Album;
import entities.media.Media;

public class SetUIState {
  private State state;
  private Media media;
  private Album album;

  public enum State {
    INITIAL,
    CLOSE_CONTEXT,
    ALBUM,
    ALBUMCONTEXT,
    ADDTOALBUM,
    DIASHOW,
    METADATA,
    IMAGES,
    EXPORT;
  }

  public SetUIState(State state, Media media) {
    this.state = state;
    this.media = media;
  }

  public SetUIState(State state, Album album) {
    this.state = state;
    this.album = album;
  }

  public SetUIState(State state) {
    this.state = state;
  }

  public State getState() {
    return this.state;
  }

  public Media getMedia() {
    return this.media;
  }
}
