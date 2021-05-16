package entities.media;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import entities.BaseEntityTest;
import entities.location.Location;
import entities.resolution.Resolution;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class MediaServiceTest extends BaseEntityTest {
  @Test
  public void create() {
    Location l = new Location();
    l.setName("Kiel");

    Resolution r = new Resolution();
    r.setHeight(1080);
    r.setWidth(1920);

    Media m = new Media();
    m.setFilename("testfile.png");
    m.setPrivate(false);
    m.setDataType(Media.DataType.PNG);
    m.setResolution(r);
    m.setLocation(l);

    Location ll = new Location();
    ll.setName("Kiel");

    Resolution rr = new Resolution();
    rr.setHeight(1080);
    rr.setWidth(1920);

    Media mm = new Media();
    mm.setFilename("otherfile.png");
    mm.setPrivate(false);
    mm.setDataType(Media.DataType.PNG);
    mm.setResolution(rr);
    mm.setLocation(ll);

    Media mmm = new Media();
    mmm.setFilename("otherfile.png");
    mmm.setPrivate(false);
    mmm.setDataType(Media.DataType.PNG);
    mmm.setResolution(rr);

    assertDoesNotThrow(
        () -> {
          this.mediaService.create(m);
          this.mediaService.create(mm);

          assertEquals(
              1, this.resolutionService.dao().queryForAll().size(), "Wrong amount of resolutions");
          assertEquals(
              1, this.locationService.dao().queryForAll().size(), "Wrong amount of locations");
          assertEquals(2, this.mediaService.dao().queryForAll().size(), "Wrong amount of media");
        });

    assertThrows(
        SQLException.class,
        () -> {
          this.mediaService.create(mmm);
          assertEquals(2, this.mediaService.dao().queryForAll().size(), "Wrong amount of media");
        });
  }
}
