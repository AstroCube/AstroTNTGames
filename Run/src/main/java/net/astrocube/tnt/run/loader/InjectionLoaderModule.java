package net.astrocube.tnt.run.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.tnt.run.floor.FloorModule;
import net.astrocube.tnt.run.game.GameModule;
import net.astrocube.tnt.translation.TranslationModule;

public class InjectionLoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new LoaderModule());
        install(new TranslationModule());
        install(new GameModule());
        install(new FloorModule());
    }

}
