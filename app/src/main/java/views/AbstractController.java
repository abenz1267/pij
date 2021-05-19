package views;

import com.google.inject.Inject;
import events.EventService;
import resources.ResourceService;

abstract class AbstractController {
  @Inject protected ResourceService resourceService;
  @Inject protected SceneService sceneService;
  @Inject protected EventService eventService;
}
