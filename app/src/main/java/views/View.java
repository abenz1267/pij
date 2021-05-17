package views;

public enum View {
  MAINVIEW("mainview"),
  SECONDVIEW("secondview");

  private final String name;

  View(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
