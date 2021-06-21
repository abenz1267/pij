package events;

import com.google.inject.ImplementedBy;

/**
 * Service to handle events.
 *
 * @author Andrej Benz
 */
@ImplementedBy(EventServiceImpl.class)
public interface EventService {
  void register(Object listener);

  void post(Object event);
}
