package entities.media;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.location.*;
import entities.person.*;
import entities.resolution.*;
import entities.tag.*;
import java.util.Date;

@DatabaseTable()
public class Media {
  @DatabaseField(generatedId = true)
  private int id;

  @DatabaseField(canBeNull = false, unique = true)
  private String name;

  @DatabaseField(canBeNull = false, unique = true)
  private String filename;

  @DatabaseField() private Date datetime;

  @DatabaseField() private String description;

  @DatabaseField(canBeNull = false)
  private boolean isPrivate;

  @DatabaseField() private int quality;

  @DatabaseField() private int duration;

  @DatabaseField(canBeNull = false)
  private DataType dataType;

  @DatabaseField(foreign = true, columnName = "location_id")
  private Location location;

  @DatabaseField(foreign = true, columnName = "resolution_id", canBeNull = false)
  private Resolution resolution;

  private Person[] persons;
  private Tag[] tags;

  public enum DataType {
    JPEG("*.jpeg"),
    PNG("*.png"),
    MP4("*.mp4"),
    RAW("*.raw"),
    AVI("*.avi");

    private String ext;

    DataType(String ext) {
      this.ext = ext;
    }

    @Override
    public String toString() {
      return this.ext;
    }
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public Date getDatetime() {
    return datetime;
  }

  public void setDatetime(Date datetime) {
    this.datetime = datetime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isPrivate() {
    return isPrivate;
  }

  public void setPrivate(boolean aPrivate) {
    isPrivate = aPrivate;
  }

  public int getQuality() {
    return quality;
  }

  public void setQuality(int quality) {
    this.quality = quality;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public Resolution getResolution() {
    return resolution;
  }

  public void setResolution(Resolution resolution) {
    this.resolution = resolution;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Person[] getPersons() {
    return persons;
  }

  public void setPersons(Person[] persons) {
    this.persons = persons;
  }

  public Tag[] getTags() {
    return tags;
  }

  public void setTags(Tag[] tags) {
    this.tags = tags;
  }

  public DataType getDataType() {
    return dataType;
  }

  public void setDataType(DataType dataType) {
    this.dataType = dataType;
  }
}
