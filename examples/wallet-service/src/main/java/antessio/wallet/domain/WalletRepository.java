package antessio.wallet.domain;

import java.util.Optional;

import antessio.eventsourcing.wallet.domain.aggregates.OwnerId;
import antessio.eventsourcing.wallet.domain.aggregates.Wallet;
import antessio.eventsourcing.wallet.domain.aggregates.WalletId;

public interface WalletRepository {

    void insertWallet(Wallet wallet);

    Optional<Wallet> getById(WalletId id);

    Optional<Wallet> getByOwnerId(OwnerId id);

    void updateWallet(Wallet wallet);

}
