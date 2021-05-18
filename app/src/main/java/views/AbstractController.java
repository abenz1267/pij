package views;

import com.google.inject.Inject;
import resources.ResourceService;

abstract class AbstractController {
  @Inject protected ResourceService resourceService;
  @Inject protected SceneService sceneService;
}
