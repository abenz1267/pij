package events;

public class TestEvent {
  private final String message;

  public TestEvent(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}
