package events;

import entities.location.Location;
import entities.media.Media;
import entities.resolution.Resolution;
import java.util.Date;

public class ShowImage {
    private int id;
    private String filename;
    private String name;
    private String description;
    private Date datetime;
    private boolean isPrivate;
    private Media.DataType dataType;
    private Location location;
    private Resolution resolution;


    public ShowImage(int id, String filename) {
        this.id = id;
        this.filename = filename;
//        this.name = name;
//        this.description = description;
//        this.datetime = datetime;
//        this.dataType = datatype;
//        this.isPrivate = isPrivate;
//        this.location = location;
//        this.resolution = resolution;
    }

    public Integer getId() {
    return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setDataType(Media.DataType dataType) {
        this.dataType = dataType;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public String getFilename() {
        return filename;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDatetime() {
        return datetime;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public Media.DataType getDataType() {
        return dataType;
    }

    public Location getLocation() {
        return location;
    }

    public Resolution getResolution() {
        return resolution;
    }
}
