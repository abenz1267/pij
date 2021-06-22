package entities.TagMedia;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.media.Media;
import entities.tag.Tag;

@DatabaseTable
public class TagMedia {

  @DatabaseField(generatedId = true)
  private int id;

  @DatabaseField(foreign = true, columnName = "tag_id", uniqueCombo = true)
  Tag tag;

  @DatabaseField(foreign = true, columnName = "media_id", uniqueCombo = true)
  Media media;

  TagMedia() {}

  public TagMedia(Tag tag, Media media) {
    this.tag = tag;
    this.media = media;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Tag getTag() {
    return tag;
  }

  public void setTag(Tag tag) {
    this.tag = tag;
  }

  public Media getMedia() {
    return media;
  }

  public void setMedia(Media media) {
    this.media = media;
  }
}
