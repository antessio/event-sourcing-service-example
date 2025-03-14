package antessio.eventsourcing.wallet.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import antessio.eventsourcing.AnnotationBasedProjectorStore;
import eventsourcing.EventStore;
import eventsourcing.aggregate.AggregateStore;


public class EventSourcingService extends antessio.eventsourcing.EventSourcingService {


    public EventSourcingService(AggregateStore aggregateStore, EventStore eventStore) {
        super(new AnnotationBasedProjectorStore(List.of(
                      "antessio.eventsourcing.wallet.projections")),
              aggregateStore, eventStore);
    }


}
