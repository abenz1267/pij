package views;

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
  ADDTOALBUMVIEW("addtoalbumview"),
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
