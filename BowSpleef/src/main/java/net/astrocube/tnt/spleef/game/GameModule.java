package net.astrocube.tnt.spleef.game;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.tnt.game.CommonGameModule;
import net.astrocube.tnt.game.DoubleJumpProvider;
import net.astrocube.tnt.game.ScoreboardProvider;

public class GameModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new CommonGameModule());
        bind(ScoreboardProvider.class).to(CoreScoreboardProvider.class);
        bind(DoubleJumpProvider.class).to(CoreDoubleJumpProvider.class);
    }

}
