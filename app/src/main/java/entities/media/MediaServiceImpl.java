package entities.media;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractDAO;
import entities.location.LocationService;
import entities.resolution.ResolutionService;
import java.sql.SQLException;
import javax.inject.Inject;

public class MediaServiceImpl extends AbstractDAO implements MediaService {
  private Dao<Media, Integer> dao = null;

  @Inject private LocationService locationService;
  @Inject private ResolutionService resolutionService;

  public Dao<Media, Integer> dao() {
    if (this.dao == null) {
      try {
        this.dao = DaoManager.createDao(AbstractDAO.source, Media.class);
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }

    return this.dao;
  }

  public void create(Media media) throws SQLException {
    this.transaction(
        () -> {
          if (media.getLocation() != null) {
            this.locationService.create(media.getLocation());
          }

          this.resolutionService.create(media.getResolution());

          this.dao().createIfNotExists(media);

          return null;
        });
  }
}
