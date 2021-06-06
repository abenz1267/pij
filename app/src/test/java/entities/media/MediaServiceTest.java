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
import entities.resolution.Resolution;
import java.io.File;
import java.sql.SQLException;
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
