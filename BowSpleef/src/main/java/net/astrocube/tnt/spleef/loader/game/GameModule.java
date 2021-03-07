package net.astrocube.tnt.spleef.loader.game;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.tnt.game.CommonGameModule;

public class GameModule extends ProtectedModule {

    public void configure() {
        install(new CommonGameModule());
    }

}
