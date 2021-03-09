package net.astrocube.tnt.lobby.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.tnt.lobby.statistic.StatisticModule;

public class InjectionLoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new LoaderModule());
        install(new StatisticModule());
    }

}
