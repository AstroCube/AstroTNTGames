package net.astrocube.tnt.run.game;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.tnt.game.*;

public class GameModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new CommonGameModule());
        bind(ScoreboardProvider.class).to(CoreScoreboardProvider.class);
    }

}
