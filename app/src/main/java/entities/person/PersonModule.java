package entities.person;

import com.google.inject.AbstractModule;

public class PersonModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(PersonService.class).to(PersonServiceImpl.class);
  }
}
