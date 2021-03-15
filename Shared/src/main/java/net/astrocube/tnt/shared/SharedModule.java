package net.astrocube.tnt.shared;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.tnt.shared.money.CoreMoneyTransactionHandler;
import net.astrocube.tnt.shared.money.MoneyTransactionHandler;
import net.astrocube.tnt.shared.perk.CoreTNTPerkProvider;
import net.astrocube.tnt.shared.perk.TNTPerkProvider;
import net.astrocube.tnt.shared.perk.configuration.CorePerkConfigurationCache;
import net.astrocube.tnt.shared.perk.configuration.PerkConfigurationCache;

public class SharedModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(MoneyTransactionHandler.class).to(CoreMoneyTransactionHandler.class);
        bind(TNTPerkProvider.class).to(CoreTNTPerkProvider.class);
        bind(PerkConfigurationCache.class).to(CorePerkConfigurationCache.class);
    }

}
