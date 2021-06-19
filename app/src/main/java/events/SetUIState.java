package events;

import entities.media.Media;

public class SetUIState {
  private State state;
  private Media media;

  public enum State {
    INITIAL,
    ALBUM,
    METADATA,
    IMAGES,
    EXPORT;
  }

  public SetUIState(State state, Media media) {
    this.state = state;
    this.media = media;
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
