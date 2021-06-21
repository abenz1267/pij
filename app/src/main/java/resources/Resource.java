package resources;

/**
 * Enum for resources bundles.
 *
 * @author Andrej Benz
 */
public enum Resource {
  GENERIC("generic"),
  CONFIG("config");

  private final String name;

  Resource(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
