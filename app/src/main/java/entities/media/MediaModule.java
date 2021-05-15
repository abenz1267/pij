package entities.media;

import com.google.inject.AbstractModule;

public class MediaModule extends AbstractModule {
  protected void configure() {
    bind(MediaService.class).to(MediaServiceImpl.class);
  }
}
