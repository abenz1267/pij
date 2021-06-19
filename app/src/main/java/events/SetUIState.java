package events;

public class SetUIState {
  private State state;

  public enum State {
    INITIAL,
    ALBUM,
    METADATA,
    IMAGES,
    EXPORT;
  }

  public SetUIState(State state) {
    this.state = state;
  }

  public State getState() {
    return this.state;
  }
}
