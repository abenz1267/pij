package events;

public class SetUIState {
  private State state;

  public enum State {
    INITIAL,
    ALBUM,
    EXPORT;
  }

  public SetUIState(State state) {
    this.state = state;
  }

  public State getState() {
    return this.state;
  }
}
