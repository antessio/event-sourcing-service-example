package antessio.wallet.infrastructure.persistence;

import java.math.BigDecimal;
import java.util.UUID;


import antessio.eventsourcing.wallet.domain.aggregates.OwnerId;
import antessio.eventsourcing.wallet.domain.aggregates.Wallet;
import antessio.eventsourcing.wallet.domain.aggregates.WalletId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class WalletEntity {

    @Id
    private UUID id;

    private UUID ownerId;

    private BigDecimal amount;

    public WalletEntity() {
    }

    public WalletEntity(Wallet wallet) {
        this.id = wallet.id().id();
        this.ownerId = wallet.ownerId().id();
        this.amount = wallet.amount();
    }

    public WalletEntity(UUID id, UUID ownerId, BigDecimal amount) {
        this.id = id;
        this.ownerId = ownerId;
        this.amount = amount;
    }


    public UUID getId(){
        return this.id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Wallet toWallet() {
        return new Wallet(
                new WalletId(this.getId()),
                new OwnerId(this.getOwnerId()),
                this.getAmount()
        );
    }

}
