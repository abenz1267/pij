package entities.media;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.j256.ormlite.table.TableUtils;
import entities.BaseEntityTest;
import entities.location.Location;
import entities.resolution.Resolution;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;

class MediaServiceTest extends BaseEntityTest {
  @Test
  void testImportExport() {
    var folder = new File("testfiles");

    assertDoesNotThrow(
        () -> {
          mediaService.importMedia(Lists.newArrayList(folder.listFiles()));
        });

    assertDoesNotThrow(
        () -> {
          var media = mediaService.dao().queryForAll();
          assertEquals(2, media.size());

          var resolutions = resolutionService.dao().queryForAll();
          assertEquals(2, resolutions.size());
        });

    assertEquals(2, folder.listFiles().length);

    assertThrows(
        SQLException.class,
        () -> {
          mediaService.importMedia(Lists.newArrayList(folder.listFiles()));
        });

    var zip = new File(resourceService.getMediaDir(), "test.zip");
    assertDoesNotThrow(
        () -> {
          mediaService.exportMedia(mediaService.dao().queryForAll(), zip.toPath());
        });

    assertTrue(zip.exists());
    assertFalse(new File(resourceService.getMediaDir(), "tmp").exists());

    assertDoesNotThrow(
        () -> {
          TableUtils.clearTable(databaseConnectionService.get(), Media.class);
          TableUtils.clearTable(databaseConnectionService.get(), Resolution.class);

          for (var f : new File(resourceService.getMediaDir()).listFiles()) {
            if (Files.getFileExtension(f.getName()).equals("png")) {
              f.delete();
            }
          }

          mediaService.importMediaFromExport(zip);
        });

    File testfilesdest = new File(resourceService.getMediaDir());

    assertEquals(3, testfilesdest.listFiles().length);
  }

  @Test
  void testRefreshAll() {
    var res = new Resolution(200, 200);
    var loc = new Location();
    loc.setName("Kiel");

    var media = new Media();
    media.setName("TestMedia");
    media.setFilename("TestMediaFileName");
    media.setDataType(Media.DataType.JPEG);
    media.setResolution(res);
    media.setLocation(loc);

    assertDoesNotThrow(
        () -> {
          mediaService.create(media);
        });

    assertDoesNotThrow(
        () -> {
          List<Media> r = mediaService.dao().queryForAll();
          assertEquals(1, r.size());
        });

    assertDoesNotThrow(
        () -> {
          List<Resolution> r = resolutionService.dao().queryForAll();
          assertEquals(1, r.size());
        });

    assertDoesNotThrow(
        () -> {
          List<Location> r = locationService.dao().queryForAll();
          assertEquals(1, r.size());
        });

    assertDoesNotThrow(
        () -> {
          var m = mediaService.dao().queryForAll().get(0);

          assertEquals(0, m.getResolution().getWidth());
          assertEquals(0, m.getResolution().getHeight());
          assertEquals(null, m.getLocation().getName());

          mediaService.refreshAll(m);
          assertEquals(200, m.getResolution().getWidth());
          assertEquals(200, m.getResolution().getHeight());
          assertEquals("Kiel", m.getLocation().getName());
        });
  }

  @Test
  void testDelete() throws SQLException {
    var res = new Resolution(200, 200);
    var loc = new Location();
    loc.setName("Kiel");

    var media = new Media();
    media.setName("TestMedia");
    media.setFilename("TestMediaFileName");
    media.setDataType(Media.DataType.JPEG);
    media.setResolution(res);
    media.setLocation(loc);

    mediaService.create(media);
    mediaService.delete(media);

    assertEquals(0, locationService.dao().queryForAll().size());
    assertEquals(0, resolutionService.dao().queryForAll().size());
    assertEquals(0, mediaService.dao().queryForAll().size());

    var media2 = new Media();
    media2.setName("TestMedia2");
    media2.setFilename("TestMediaFileName2");
    media2.setDataType(Media.DataType.JPEG);
    media2.setResolution(res);
    media2.setLocation(loc);

    mediaService.create(media);
    mediaService.create(media2);
    mediaService.delete(media);

    assertEquals(1, locationService.dao().queryForAll().size());
    assertEquals(1, resolutionService.dao().queryForAll().size());
    assertEquals(1, mediaService.dao().queryForAll().size());

    mediaService.delete(media2);
    assertEquals(0, locationService.dao().queryForAll().size());
    assertEquals(0, resolutionService.dao().queryForAll().size());
    assertEquals(0, mediaService.dao().queryForAll().size());
  }

  @Test
  void testFilterMediaByInput() throws SQLException {
    var input = "MediaFileName";
    var input2 = "Media2";
    var res = new Resolution(200, 200);

    var media = new Media();
    media.setName("TestMedia");
    media.setFilename("TestMediaFileName");
    media.setDataType(Media.DataType.JPEG);
    media.setResolution(res);
    mediaService.dao().create(media);

    var media2 = new Media();
    media2.setName("TestMedia2");
    media2.setFilename("TestMediaFileName2");
    media2.setDataType(Media.DataType.PNG);
    media2.setResolution(res);
    mediaService.dao().create(media2);

    assertDoesNotThrow(
        () -> {
          mediaService.filterMediaByInput(input);
        });

    assertEquals(2, mediaService.filterMediaByInput(input).size());

    assertEquals(1, mediaService.filterMediaByInput(input2).size());
    assertEquals(media2.getName(), mediaService.filterMediaByInput(input2).get(0).getName());

    assertThrows(
        NullPointerException.class,
        () -> {
          mediaService.filterMediaByInput(null);
        });
  }
}
