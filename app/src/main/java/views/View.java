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
  ALBUMCONTEXT("albumcontext"),
  NEWALBUM("newalbum"),
  ALBUMVIEW("albumview"),
  IMAGESVIEW("imagesview"),
  CLEAR("CLEAR"),
  EXPORTCONTEXT("exportcontext"),
  METADATAVIEW("metadataview"),
  ADDTOALBUMCONTEXT("addtoalbumcontext"),
  DIASHOW("diashowcontext");

  private final String name;

  View(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
