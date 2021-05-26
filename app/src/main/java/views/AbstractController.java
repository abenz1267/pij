package views;

import com.google.inject.Inject;
import entities.media.MediaService;
import events.EventService;
import resources.ResourceService;

abstract class AbstractController {
  @Inject protected ResourceService resourceService;
  @Inject protected SceneService sceneService;
  @Inject protected EventService eventService;
  @Inject protected MediaService mediaService;
}
