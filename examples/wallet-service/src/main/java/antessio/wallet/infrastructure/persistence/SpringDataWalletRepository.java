package antessio.wallet.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SpringDataWalletRepository extends JpaRepository<WalletEntity, UUID> {

    Optional<WalletEntity> findByOwnerId(UUID ownerId);

}
