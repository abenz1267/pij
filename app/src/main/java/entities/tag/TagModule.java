package entities.tag;

import com.google.inject.AbstractModule;

public class TagModule extends AbstractModule {
  protected void configure() {
    bind(TagService.class).to(TagServiceImpl.class);
  }
}
