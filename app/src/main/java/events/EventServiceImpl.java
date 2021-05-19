package events;

import com.google.common.eventbus.EventBus;
import javax.inject.Singleton;

@Singleton
public class EventServiceImpl implements EventService {
  private final EventBus bus;

  EventServiceImpl() {
    this.bus = new EventBus();
  }

  public void register(Object listener) {
    bus.register(listener);
  }

  public void post(Object event) {
    bus.post(event);
  }
}
