package antessio.eventsourcing.wallet.domain.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import antessio.eventsourcing.wallet.domain.aggregates.Wallet;
import eventsourcing.Event;

public record WalletCreatedEvent (UUID eventId, UUID walletId, UUID ownerId, BigDecimal amount, Instant occurredAt) implements Event<Wallet> {


    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Instant getOccurredAt() {
        return occurredAt;
    }

    @Override
    public String getAggregateId() {
        return walletId.toString();
    }

    @Override
    public Class<? extends Wallet> getAggregateClass() {
        return Wallet.class;
    }

}
