package antessio.wallet.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataEventRepository extends JpaRepository<EventEntity, String> {

    List<EventEntity> findByAggregateType(String aggregateType);

    List<EventEntity> findByProcessed(boolean processed);

}

