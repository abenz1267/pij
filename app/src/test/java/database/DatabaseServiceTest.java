package database;

import static org.junit.Assert.*;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;

public class DatabaseServiceTest {
  @Inject private DatabaseService toTest;

  @Before
  public void setup() {
    Injector injector = Guice.createInjector(new DatabaseModule());
    injector.injectMembers(this);
  }

  @Test
  public void testHello() {
    assertEquals(toTest.hello(), "Hello World!");
  }
}
