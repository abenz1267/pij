package events;

import com.google.inject.ImplementedBy;

@ImplementedBy(EventServiceImpl.class)
public interface EventService {
  void register(Object listener);

  void post(Object event);
}
