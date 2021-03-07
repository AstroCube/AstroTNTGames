package net.astrocube.tnt.spleef.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.tnt.podium.CoreMatchProgressHandler;
import net.astrocube.tnt.podium.MatchProgressHandler;
import net.astrocube.tnt.spleef.loader.game.GameModule;
import net.astrocube.tnt.translation.TranslationModule;

public class InjectionLoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new LoaderModule());
        install(new TranslationModule());
        install(new GameModule());

        bind(MatchProgressHandler.class).to(CoreMatchProgressHandler.class);

    }

}
