package entities.resolution;

import com.google.inject.AbstractModule;

public class ResolutionModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(ResolutionService.class).to(ResolutionServiceImpl.class);
  }
}
