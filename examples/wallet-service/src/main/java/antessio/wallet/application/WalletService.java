package antessio.wallet.application;

import java.util.Optional;

import antessio.eventsourcing.wallet.domain.aggregates.Wallet;
import antessio.eventsourcing.wallet.domain.aggregates.WalletId;
import antessio.eventsourcing.wallet.domain.commands.CreateWalletCommand;
import antessio.eventsourcing.wallet.domain.commands.TopUpWalletCommand;
import antessio.wallet.domain.WalletDomainService;
import antessio.wallet.domain.WalletRepository;

public class WalletService {

    private final WalletDomainService walletDomainService;
    private final WalletRepository walletRepository;

    public WalletService(WalletDomainService walletDomainService, WalletRepository walletRepository) {
        this.walletDomainService = walletDomainService;
        this.walletRepository = walletRepository;
    }

    public WalletId createWallet(CreateWalletCommand createWalletCommand){
        return walletDomainService.createWallet(createWalletCommand)
                .id();
    }

    public void topUpWallet(TopUpWalletCommand topUpWalletCommand){
        walletDomainService.topUpWallet(topUpWalletCommand);
    }

    public Optional<Wallet> getWallet(WalletId walletId){
        return walletRepository.getById(walletId);
    }
}
