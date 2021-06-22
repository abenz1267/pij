package entities.albummedia;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.album.Album;
import entities.media.Media;

/**
 * AlbumMedia entity.
 *
 * @author Timm Lohmann
 * @author Phillip Knutzen
 * @author Joey Wille
 */
@DatabaseTable()
public class AlbumMedia {

  public static final String ALBUM_ID_FIELD_NAME = "album_id";
  public static final String MEDIA_ID_FIELD_NAME = "media_id";

  @DatabaseField(generatedId = true)
  private int id;

  @DatabaseField(foreign = true, columnName = ALBUM_ID_FIELD_NAME)
  Album album;

  @DatabaseField(foreign = true, columnName = MEDIA_ID_FIELD_NAME)
  Media media;

  AlbumMedia() {}

  public AlbumMedia(Album album, Media media) {
    this.album = album;
    this.media = media;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Album getAlbum() {
    return album;
  }

  public void setAlbum(Album album) {
    this.album = album;
  }

  public Media getMedia() {
    return media;
  }

  public void setMedia(Media media) {
    this.media = media;
  }
}
