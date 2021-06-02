package views;

public enum View {
  MAINVIEW("mainview"),
  SECONDVIEW("secondview"),
  IMAGESVIEW("imagesview"),
  ALBUMCONTEXT("albumcontext");

  private final String name;

  View(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
