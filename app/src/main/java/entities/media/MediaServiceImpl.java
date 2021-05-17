package entities.media;

import com.google.inject.Singleton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractEntityService;
import entities.location.Location;
import entities.location.LocationService;
import entities.resolution.Resolution;
import entities.resolution.ResolutionService;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.inject.Inject;

@Singleton
public class MediaServiceImpl extends AbstractEntityService implements MediaService {
  @Inject private LocationService locationService;
  @Inject private ResolutionService resolutionService;
  private Dao<Media, Integer> dao = null;

  public Dao<Media, Integer> dao() {
    if (this.dao == null) {
      try {
        this.dao = DaoManager.createDao(this.databaseConnectionService.get(), Media.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.dao;
  }

  public void create(Media media) throws SQLException {
    this.transaction(
        () -> {
          this.checkLocation(media);
          this.checkResolution(media);

          this.dao().createIfNotExists(media);

          return null;
        });
  }

  private void checkLocation(Media media) throws SQLException {
    if (media.getLocation() != null) {
      List<Location> res =
          this.locationService.dao().queryForEq("name", media.getLocation().getName());

      if (!res.isEmpty()) {
        media.setLocation(res.get(0));
      } else {
        this.locationService.dao().createIfNotExists(media.getLocation());
      }
    }
  }

  private void checkResolution(Media media) throws SQLException {
    Map<String, Object> kv = new HashMap<>();
    kv.put("height", media.getResolution().getHeight());
    kv.put("width", media.getResolution().getWidth());

    List<Resolution> res = this.resolutionService.dao().queryForFieldValuesArgs(kv);

    if (!res.isEmpty()) {
      media.setResolution(res.get(0));
    } else {
      this.resolutionService.dao().createIfNotExists(media.getResolution());
    }
  }
}
