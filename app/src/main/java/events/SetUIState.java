package events;

public class SetUIState {
  private State state;

  public enum State {
    INITIAL,
    CLOSE_CONTEXT,
    ALBUM,
    EXPORT,
    DIASHOW;
  }

  public SetUIState(State state) {
    this.state = state;
  }

  public State getState() {
    return this.state;
  }
}
