package net.astrocube.tnt.lobby.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.tnt.lobby.menu.MenuModule;
import net.astrocube.tnt.lobby.statistic.StatisticModule;
import net.astrocube.tnt.shared.SharedModule;
import net.astrocube.tnt.shared.translation.TranslationModule;

public class InjectionLoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new TranslationModule());
        install(new LoaderModule());
        install(new StatisticModule());
        install(new SharedModule());
        install(new MenuModule());
    }

}
