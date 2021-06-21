package views;

/**
 * Enum for all loadable views.
 *
 * @author Andrej Benz
 * @author Timm Lohmann
 * @author Joey Wille
 * @author Phillip Knutzen
 * @author Christian Paulsen
 * @author Kelvin Leclaire
 */
public enum View {
  MAINVIEW("mainview"),
  SECONDVIEW("secondview"),
  IMAGESVIEW("imagesview"),
  ALBUMCONTEXT("albumcontext"),
  CLEAR("CLEAR"),
  DIASHOW("diashowcontext"),
  EXPORTCONTEXT("exportcontext");

  private final String name;

  View(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
