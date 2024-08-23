package antessio.eventsourcing;

import java.util.List;

/**
 * Stores all the events
 * @param <A>
 * @param <ID>
 */
public interface EventStore<A extends Aggregate<ID>, ID> {

    void put(List<Event<A>> events);
    List<Event<A>> getAllEvents();
    List<Event<A>> getAggregateEvents(Class<? extends A> aggregateClass);


}
