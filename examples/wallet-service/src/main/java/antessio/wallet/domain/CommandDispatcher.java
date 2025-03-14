package antessio.wallet.domain;

import antessio.eventsourcing.wallet.domain.aggregates.Wallet;
import antessio.eventsourcing.wallet.domain.commands.CreateWalletCommand;
import antessio.eventsourcing.wallet.domain.commands.TopUpWalletCommand;

public interface CommandDispatcher {

    Wallet createWallet(CreateWalletCommand createWalletCommand);

    Wallet topUpWallet(TopUpWalletCommand topUpWalletCommand);

}
