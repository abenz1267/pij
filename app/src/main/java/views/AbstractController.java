package views;

import com.google.inject.Inject;
import entities.album.AlbumService;
import entities.media.MediaService;
import events.EventService;
import java.util.logging.Logger;
import resources.ResourceService;

abstract class AbstractController {
  @Inject protected ResourceService resourceService;
  @Inject protected SceneService sceneService;
  @Inject protected EventService eventService;
  @Inject protected MediaService mediaService;
  @Inject protected Logger logger;
  @Inject protected AlbumService albumService;
  @Inject protected MediaViewController mediaViewController;
}
