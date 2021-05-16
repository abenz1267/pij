package entities.media;

import com.google.inject.AbstractModule;
import entities.location.LocationService;
import entities.location.LocationServiceImpl;
import entities.resolution.ResolutionService;
import entities.resolution.ResolutionServiceImpl;

public class MediaModule extends AbstractModule {
  protected void configure() {
    bind(MediaService.class).to(MediaServiceImpl.class);
    bind(LocationService.class).to(LocationServiceImpl.class);
    bind(ResolutionService.class).to(ResolutionServiceImpl.class);
  }
}
