package net.astrocube.tnt.game;

import me.fixeddev.inject.ProtectedModule;

public class CommonGameModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(PlayerSpawner.class).to(CorePlayerSpawner.class);
    }

}
