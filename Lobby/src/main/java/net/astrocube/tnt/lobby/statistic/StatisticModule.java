package net.astrocube.tnt.lobby.statistic;

import me.fixeddev.inject.ProtectedModule;

public class StatisticModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(LobbyScoreboardProvider.class).to(CoreLobbyScoreboardProvider.class);
        bind(ModeVictoryProvider.class).to(CoreModeVictoryProvider.class);
        bind(MoneyTransactionHandler.class).to(CoreMoneyTransactionHandler.class);
    }

}
