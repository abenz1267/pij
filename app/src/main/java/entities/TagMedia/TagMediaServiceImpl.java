package entities.TagMedia;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractEntityService;
import entities.media.Media;
import entities.tag.Tag;
import entities.tag.TagService;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TagMediaServiceImpl extends AbstractEntityService implements TagMediaService {

  @Inject private TagService tagService;

  private Dao<TagMedia, Integer> dao = null;

  public Dao<TagMedia, Integer> dao() {
    if (this.dao == null) {
      try {
        this.dao = DaoManager.createDao(this.databaseConnectionService.get(), TagMedia.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.dao;
  }

  @Override
  public void remove(Tag tag, Media media) throws SQLException {
    var result = this.get(tag, media);

    dao().delete(result.get(0));
    var others = dao().queryForEq("tag_id", tag.getId());
    if (others.isEmpty()) {
      tagService.dao().delete(tag);
    }
  }

  @Override
  public List<TagMedia> get(Tag tag, Media media) throws SQLException {

    var qb = dao().queryBuilder();
    qb.where().eq("tag_id", tag.getId()).and().eq("media_id", media.getId());

    return qb.query();
  }

  @Override
  public List<Tag> getTags(Media media) throws SQLException {
    var qb = dao().queryBuilder();
    var mQb = tagService.dao().queryBuilder();

    qb.selectColumns("tag_id");
    qb.where().eq("media_id", media.getId());

    mQb.where().in("id", qb);

    return mQb.query();
  }
}
