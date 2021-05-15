package database;

import com.google.inject.AbstractModule;

public class DatabaseModule extends AbstractModule {
  protected void configure() {
    bind(DatabaseService.class).to(DatabaseServiceImpl.class);
  }
}
