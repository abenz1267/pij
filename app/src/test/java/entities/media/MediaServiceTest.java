package entities.media;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.collect.Lists;
import entities.BaseEntityTest;
import java.io.File;
import org.junit.jupiter.api.Test;

class MediaServiceTest extends BaseEntityTest {
  @Test
  void testImport() {
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
  }
}
