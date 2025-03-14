package antessio.wallet.infrastructure;

import org.springframework.stereotype.Component;

import antessio.eventsourcing.wallet.domain.EventSourcingService;
import antessio.eventsourcing.wallet.domain.aggregates.Wallet;
import antessio.eventsourcing.wallet.domain.commands.CreateWalletCommand;
import antessio.eventsourcing.wallet.domain.commands.TopUpWalletCommand;
import antessio.wallet.domain.CommandDispatcher;

@Component
public class CommandDispatcherAdapter implements CommandDispatcher {

    private final EventSourcingService eventSourcingService;

    public CommandDispatcherAdapter(EventSourcingService eventSourcingService) {
        this.eventSourcingService = eventSourcingService;
    }

    @Override
    public Wallet createWallet(CreateWalletCommand createWalletCommand) {
        return eventSourcingService.publish(createWalletCommand);
    }

    @Override
    public Wallet topUpWallet(TopUpWalletCommand topUpWalletCommand) {
        return eventSourcingService.publish(topUpWalletCommand);
    }

}
