package antessio.wallet.domain;

import antessio.eventsourcing.wallet.domain.aggregates.OwnerId;
import antessio.eventsourcing.wallet.domain.aggregates.Wallet;
import antessio.eventsourcing.wallet.domain.commands.CreateWalletCommand;
import antessio.eventsourcing.wallet.domain.commands.TopUpWalletCommand;

public class WalletDomainService {
    private final CommandDispatcher commandDispatcher;
    private final WalletRepository walletRepository;
    public WalletDomainService(CommandDispatcher commandDispatcher, WalletRepository walletRepository) {
        this.commandDispatcher = commandDispatcher;
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletCommand createWalletCommand){
        if(walletRepository.getByOwnerId(new OwnerId(createWalletCommand.ownerId())).isPresent()){
            throw new IllegalArgumentException("owner "+createWalletCommand.ownerId()+" has already a wallet");
        }
        return commandDispatcher.createWallet(createWalletCommand);
    }

    public Wallet topUpWallet(TopUpWalletCommand topUpWalletCommand){
        return commandDispatcher.topUpWallet(topUpWalletCommand);
    }



}
