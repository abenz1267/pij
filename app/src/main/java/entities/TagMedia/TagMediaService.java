package entities.TagMedia;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import entities.media.Media;
import entities.tag.Tag;

import java.sql.SQLException;
import java.util.List;

@ImplementedBy(TagMediaServiceImpl.class)
public interface TagMediaService {
    Dao<TagMedia, Integer> dao();

    void remove(Tag tag, Media media) throws SQLException;

    List<TagMedia> get(Tag tag, Media media) throws SQLException;
}
