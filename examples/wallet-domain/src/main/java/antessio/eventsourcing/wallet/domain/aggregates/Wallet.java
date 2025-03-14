package antessio.eventsourcing.wallet.domain.aggregates;


import java.math.BigDecimal;

import eventsourcing.aggregate.Aggregate;

public record Wallet(WalletId id, OwnerId ownerId, BigDecimal amount) implements Aggregate {


    @Override
    public String getId() {
        return id.toString();
    }

}
