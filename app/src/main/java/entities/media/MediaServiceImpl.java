package entities.media;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractDAO;
import entities.location.Location;
import entities.location.LocationService;
import entities.resolution.Resolution;
import entities.resolution.ResolutionService;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

public class MediaServiceImpl extends AbstractDAO implements MediaService {
  private static final Logger logger = Logger.getLogger(MediaServiceImpl.class.getName());
  private Dao<Media, Integer> dao = null;

  @Inject private LocationService locationService;
  @Inject private ResolutionService resolutionService;

  public Dao<Media, Integer> dao() {
    if (this.dao == null) {
      try {
        this.dao = DaoManager.createDao(AbstractDAO.source, Media.class);
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
