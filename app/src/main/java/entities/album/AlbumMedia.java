package entities.album;

import com.j256.ormlite.field.DatabaseField;
import entities.media.Media;

public class AlbumMedia {

    public final static String ALBUM_ID_FIELD_NAME = "album_id";
    public final static String MEDIA_ID_FIELD_NAME = "media_id";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, columnName = ALBUM_ID_FIELD_NAME)
    Album album;

    @DatabaseField(foreign = true, columnName = MEDIA_ID_FIELD_NAME)
    Media media;

    AlbumMedia() {

    }

    public AlbumMedia(Album album, Media media) {
        this.album = album;
        this.media = media;
    }
}
