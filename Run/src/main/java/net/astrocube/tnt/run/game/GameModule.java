package net.astrocube.tnt.run.game;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.tnt.run.game.CorePlayerSpawner;
import net.astrocube.tnt.run.game.PlayerSpawner;

public class GameModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(PlayerSpawner.class).to(CorePlayerSpawner.class);
        bind(ScoreboardProvider.class).to(CoreScoreboardProvider.class);
    }

}
