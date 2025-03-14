package antessio.eventsourcing.wallet.domain.commands;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import antessio.eventsourcing.wallet.domain.EventSourcingService;
import antessio.eventsourcing.wallet.domain.aggregates.Wallet;
import antessio.eventsourcing.wallet.domain.events.WalletCreatedEvent;
import antessio.eventsourcing.wallet.domain.events.WalletTopUpExecutedEvent;
import eventsourcing.Command;
import eventsourcing.Event;

public record TopUpWalletCommand(UUID walletId, BigDecimal amount) implements Command<Wallet> {


    @Override
    public Optional<String> getAggregateId() {
        return Optional.of(walletId.toString());
    }

    @Override
    public List<Event<Wallet>> process() {
        return List.of(
                new WalletTopUpExecutedEvent(
                        UUID.randomUUID(),
                        walletId,
                        amount,
                        Instant.now()
                )
        );
    }

    @Override
    public Class<Wallet> getAggregateClass() {
        return Wallet.class;
    }

}
