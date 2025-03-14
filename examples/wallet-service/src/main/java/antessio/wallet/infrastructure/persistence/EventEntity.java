package antessio.wallet.infrastructure.persistence;

import java.time.Instant;
import java.util.Map;

import eventsourcing.Event;
import eventsourcing.aggregate.Aggregate;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "event")
public class EventEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "aggregate_id", nullable = false, length = 36)
    private String aggregateId;

    @Column(name = "object", nullable = false)
    private String object;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "aggregate_type", nullable = false)
    private String aggregateType;

    @Column(name = "processed", nullable = false)
    private boolean processed;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    public EventEntity(String id, String aggregateId, String object, String type, String aggregateType, boolean processed, Instant occurredAt) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.object = object;
        this.type = type;
        this.aggregateType = aggregateType;
        this.processed = processed;
        this.occurredAt = occurredAt;
    }

    public String getId() {
        return id;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public String getObject() {
        return object;
    }

    public String getType() {
        return type;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public boolean isProcessed() {
        return processed;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public EventEntity setProcessed() {
        this.processed = true;
        return this;
    }

}
