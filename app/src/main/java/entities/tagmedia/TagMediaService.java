package entities.tagmedia;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import entities.media.Media;
import entities.tag.Tag;
import java.sql.SQLException;
import java.util.List;

/**
 * Service to handle tagMedia entitites.
 *
 * @author Timm Lohmann
 * @author Phillip Knutzen
 * @author Joey Wille
 */
@ImplementedBy(TagMediaServiceImpl.class)
public interface TagMediaService {
  Dao<TagMedia, Integer> dao();

  /**
   * Removes the connection between a given {@link Tag} entity and a given {@link Media} entity to
   * remove Tags from a certain media file.
   *
   * @param tag the tag entity.
   * @param media the media entity.
   * @throws SQLException if there's a problem with the database.
   */
  void remove(Tag tag, Media media) throws SQLException;

  /**
   * Gets a List of connections between {@link Tag} entitites and given {@link Media} entity.
   *
   * @param tag the tag entities.
   * @param media the media entities.
   * @throws SQLException if there's a problem with the Database.
   */
  List<TagMedia> get(Tag tag, Media media) throws SQLException;

  /**
   * Gets a List a List with {@link Tag} entities which are connected to a given {@link Media}
   * entities.
   *
   * @param media the media entities.
   * @return returns the given list.
   * @throws SQLException if there's a problem with the database.
   */
  List<Tag> getTags(Media media) throws SQLException;
}
