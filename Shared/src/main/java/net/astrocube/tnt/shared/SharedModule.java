package net.astrocube.tnt.shared;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.tnt.shared.money.CoreMoneyTransactionHandler;
import net.astrocube.tnt.shared.money.MoneyTransactionHandler;
import net.astrocube.tnt.shared.perk.CorePerkManifestProvider;
import net.astrocube.tnt.shared.perk.PerkManifestProvider;

public class SharedModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(MoneyTransactionHandler.class).to(CoreMoneyTransactionHandler.class);
        bind(PerkManifestProvider.class).to(CorePerkManifestProvider.class);
    }

}
