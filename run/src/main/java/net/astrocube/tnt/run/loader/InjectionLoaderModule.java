package net.astrocube.tnt.run.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.translation.TranslationModule;
import net.astrocube.tnt.podium.CoreMatchProgressHandler;
import net.astrocube.tnt.podium.MatchProgressHandler;
import net.astrocube.tnt.run.floor.FloorModule;
import net.astrocube.tnt.run.game.GameModule;
import net.astrocube.tnt.shared.SharedModule;

public class InjectionLoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new LoaderModule());
        install(new TranslationModule());
        install(new GameModule());
        install(new FloorModule());
        install(new SharedModule());

        bind(MatchProgressHandler.class).to(CoreMatchProgressHandler.class);

    }

}
