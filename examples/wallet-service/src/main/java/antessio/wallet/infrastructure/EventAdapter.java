package antessio.wallet.infrastructure;

import org.springframework.stereotype.Component;

import antessio.eventsourcing.jsonconversion.JsonConverter;
import antessio.wallet.infrastructure.persistence.EventEntity;
import eventsourcing.Event;
import eventsourcing.aggregate.Aggregate;

@Component
public class EventAdapter {

    private final JsonConverter jsonConverter;


    public EventAdapter(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    public <A extends Aggregate> Event<A> toEvent(EventEntity eventEntity) {
        return jsonConverter.fromJson(eventEntity.getObject(), Event.class);
    }

    public <A extends Aggregate> EventEntity fromEvent(Event<A> event) {
        String eventJson = jsonConverter.toJson(event);
        return new EventEntity(
                event.getEventId().toString(),
                event.getAggregateId(),
                eventJson,
                event.getAggregateClass().getCanonicalName(),
                event.getClass().getCanonicalName(),
                false,
                event.getOccurredAt()
        );


    }

}
