package entities.location;

import com.google.inject.AbstractModule;

public class LocationModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(LocationService.class).to(LocationServiceImpl.class);
  }
}
