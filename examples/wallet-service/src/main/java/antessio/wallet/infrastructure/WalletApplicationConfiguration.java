package antessio.wallet.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import antessio.eventsourcing.jsonconversion.JsonConverter;
import antessio.eventsourcing.wallet.domain.EventSourcingService;
import antessio.eventsourcing.wallet.domain.aggregates.Wallet;
import antessio.wallet.application.WalletService;
import antessio.wallet.domain.CommandDispatcher;
import antessio.wallet.domain.WalletDomainService;
import antessio.wallet.domain.WalletRepository;
import antessio.wallet.infrastructure.persistence.EventEntity;
import antessio.wallet.infrastructure.persistence.SpringDataEventRepository;
import antessio.wallet.infrastructure.persistence.SpringDataWalletRepository;
import antessio.wallet.infrastructure.persistence.WalletEntity;
import eventsourcing.Event;
import eventsourcing.EventStore;
import eventsourcing.aggregate.Aggregate;
import eventsourcing.aggregate.AggregateStore;

@Configuration
public class WalletApplicationConfiguration {

    @Bean
    public WalletService walletService(
            CommandDispatcher commandDispatcher,
            WalletRepository walletRepository) {
        return new WalletService(new WalletDomainService(
                commandDispatcher,
                walletRepository
        ), walletRepository);
    }

    @Bean
    public EventSourcingService eventSourcingService(
            AggregateStore walletEntityAggregateStore,
            EventStore eventStore) {
        return new EventSourcingService(
                walletEntityAggregateStore,
                eventStore
        );
    }

    @Bean
    public JsonConverter jsonConverter(){
        return new JacksonJsonConverter();
    }
    @Bean
    public EventStore eventStore(SpringDataEventRepository springDataEventRepository, JsonConverter jsonConverter) {
        EventAdapter eventAdapter = new EventAdapter(jsonConverter);
        return new EventStore() {
            @Override
            public <A extends Aggregate> void put(List<Event<A>> list) {
                springDataEventRepository.saveAll(list
                                                          .stream().map(eventAdapter::fromEvent).toList());

            }

            @Override
            public <A extends Aggregate> List<Event<A>> getAllEvents() {
                return springDataEventRepository.findAll()
                                                .stream()
                                                .map(e -> (Event<A>) eventAdapter.toEvent(e))
                                                .toList();
            }

            @Override
            public <A extends Aggregate> List<Event<A>> getAggregateEvents(Class<? extends A> aClass) {
                return springDataEventRepository.findByAggregateType(aClass.getCanonicalName())
                                                .stream()
                                                .map(e -> (Event<A>) eventAdapter.toEvent(e))
                                                .toList();
            }

            @Override
            public <A extends Aggregate> List<Event<A>> getUnprocessedEvents() {
                return springDataEventRepository.findByProcessed(false)
                                                .stream()
                                                .map(e -> (Event<A>) eventAdapter.toEvent(e))
                                                .toList();
            }

            @Override
            public <A extends Aggregate> void markAsProcessed(List<Event<A>> list) {
                List<EventEntity> updated = springDataEventRepository.findAllById(list.stream().map(Event::getEventId).map(UUID::toString).toList())
                                                                     .stream()
                                                                     .map(EventEntity::setProcessed)
                                                                     .toList();
                springDataEventRepository.saveAll(updated);
            }
        };
    }

    @Bean
    public AggregateStore aggregateStore(SpringDataWalletRepository walletRepository) {


        return new AggregateStore() {
            @Override
            public <A extends Aggregate> Optional<A> get(String s, Class<? extends A> aClass) {
                if (aClass.equals(Wallet.class)) {
                    return (Optional<A>) walletRepository.findById(UUID.fromString(s))
                                                         .map(WalletEntity::toWallet);
                }
                return Optional.empty();
            }

            @Override
            public <A extends Aggregate> void put(A a) {
                if (a instanceof Wallet wallet) {
                    walletRepository.save(new WalletEntity(wallet));
                }
            }

        };
    }


}
