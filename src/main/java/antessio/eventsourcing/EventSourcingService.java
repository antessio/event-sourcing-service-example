package antessio.eventsourcing;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * This allows the source to publish a {@link Command} that triggers an {@link Event} that eventually
 * is projected, through a {@link Projector}, on a {@link Aggregate}
 * @param <A>
 * @param <ID>
 */
public interface EventSourcingService<A extends Aggregate<ID>, ID> {

    /**
     * Where the {@link Projector}s are stored.
     * @return
     */
    ProjectorStore<A, ID> getProjectorStore();

    /**
     * Where the {@link Aggregate}s are stored.
     * @return
     */
    AggregateStore<A, ID> getAggregateStore();

    /**
     * Where the {@link Event}s are stored.
     * @return
     */
    EventStore<A, ID> getEventStore();

    /**
     * Send a {@link Command} and returns the updated {@link Aggregate}
     * @param command
     * @return
     */
    default A publish(Command<A, ID> command) {
        Optional<A> maybeAggregate = command.getAggregateId()
                                            .flatMap(aggregateId -> getAggregateStore().get(aggregateId));
        List<Event<A>> eventsToApply = command.process();
        Function<A, A> result = eventsToApply.stream()
                                             .map(this::applyEvent)
                                             .reduce(Function::compose)
                                             .orElseGet(Function::identity);
        A updatedAggregate = result.apply(maybeAggregate.orElse(null));
        getEventStore().put(eventsToApply);
        getAggregateStore().put(updatedAggregate);
        return updatedAggregate;
    }


    /**
     * Register a {@link Projector} to handle {@link Event}
     * @param projector
     * @param <E>
     */
    default <E extends Event<A>> void registerProjector(Projector<A, E> projector) {
        getProjectorStore().addProjector((Projector<A, Event<A>>) projector);
    }

    /**
     * Returns the {@link Aggregate} state
     * @param id
     * @return
     */
    default Optional<A> getAggregate(ID id) {
        return getAggregateStore().get(id);
    }

    private Function<A, A> applyEvent(Event<A> event) {
        return (a) -> {
            Projector<A, Event<A>> matchingProjector = getProjectorStore().getMatchingProjector((Class<? extends Event<A>>) event.getClass());
            return matchingProjector.handle(a, event);
        };
    }

}
