package net.astrocube.tnt.lobby.statistic;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.tnt.shared.money.CoreMoneyTransactionHandler;
import net.astrocube.tnt.shared.money.MoneyTransactionHandler;

public class StatisticModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(LobbyScoreboardProvider.class).to(CoreLobbyScoreboardProvider.class);
        bind(ModeVictoryProvider.class).to(CoreModeVictoryProvider.class);
        bind(MoneyTransactionHandler.class).to(CoreMoneyTransactionHandler.class);
    }

}
