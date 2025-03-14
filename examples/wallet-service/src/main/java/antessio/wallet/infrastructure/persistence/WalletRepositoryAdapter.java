package antessio.wallet.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import antessio.eventsourcing.wallet.domain.aggregates.*;
import antessio.wallet.domain.WalletRepository;

@Component
public class WalletRepositoryAdapter implements WalletRepository {
    private final SpringDataWalletRepository springDataWalletRepository;

    public WalletRepositoryAdapter(SpringDataWalletRepository springDataWalletRepository) {
        this.springDataWalletRepository = springDataWalletRepository;
    }

    @Override
    public void insertWallet(Wallet wallet) {
        WalletEntity walletEntity = new WalletEntity(wallet);
        springDataWalletRepository.save(walletEntity);

    }

    @Override
    public Optional<Wallet> getById(WalletId id) {
        return springDataWalletRepository.findById(id.id())
                .map(WalletEntity::toWallet);
    }

    @Override
    public Optional<Wallet> getByOwnerId(OwnerId id) {
        return springDataWalletRepository.findByOwnerId(id.id())
                .map(WalletEntity::toWallet);
    }

    @Override
    public void updateWallet(Wallet wallet) {
        springDataWalletRepository.save(new WalletEntity(wallet));
    }

}
