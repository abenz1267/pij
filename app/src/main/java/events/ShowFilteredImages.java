package events;

public class ShowFilteredImages {
  String searchInput;

  public ShowFilteredImages(String searchInput) {
    this.searchInput = searchInput;
  }

  public String getSearchInput() {
    return searchInput;
  }
}
