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
