package resources;

import com.google.inject.AbstractModule;

public class ResourceModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(ResourceService.class).to(ResourceServiceImpl.class);
  }
}
