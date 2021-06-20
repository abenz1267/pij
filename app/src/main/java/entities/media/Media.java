package entities.media;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.io.Files;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.location.Location;
import entities.person.Person;
import entities.resolution.Resolution;
import entities.tag.Tag;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;

@DatabaseTable()
public class Media {
  @DatabaseField(generatedId = true)
  @JsonIgnore
  private int id;

  @DatabaseField(canBeNull = false, unique = true)
  private String name;

  @DatabaseField(canBeNull = false, unique = true)
  private String filename;

  @DatabaseField(format = "dd.MM.yyyy")
  private Date datetime;

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

  private List<Person> persons = new ArrayList<>();
  private Tag[] tags;

  public enum DataType {
    JPEG("*.jpeg"),
    JPG("*.jpg"),
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

  Media() {}

  public Media(File file, String outputDir) throws IOException {
    DataType datatype = null;
    var ext = Files.getFileExtension(file.getName());

    var img = ImageIO.read(file);
    var res = new Resolution(img.getWidth(), img.getHeight());

    this.setFilename(new File(outputDir, file.getName()).getPath());
    this.setName(this.getFilename());

    for (var d : DataType.values()) {
      if (d.toString().contains(ext)) {
        datatype = d;
      }
    }

    this.setDataType(datatype);
    this.setResolution(res);
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

  public List<Person> getPersons() {
    return persons;
  }

  public void setPersons(List<Person> persons) {
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
