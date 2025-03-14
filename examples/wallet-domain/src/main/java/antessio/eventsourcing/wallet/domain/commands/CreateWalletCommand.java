package antessio.eventsourcing.wallet.domain.commands;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import antessio.eventsourcing.wallet.domain.aggregates.Wallet;
import antessio.eventsourcing.wallet.domain.events.WalletCreatedEvent;
import eventsourcing.Command;
import eventsourcing.Event;


public record CreateWalletCommand(UUID ownerId) implements Command<Wallet> {

    @Override
    public Optional<String> getAggregateId() {
        return Optional.empty();
    }

    @Override
    public List<Event<Wallet>> process() {
        return List.of(
                new WalletCreatedEvent(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        ownerId,
                        BigDecimal.ZERO,
                        Instant.now()
                )
        );
    }

    @Override
    public Class<Wallet> getAggregateClass() {
        return null;
    }

}
