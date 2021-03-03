package net.astrocube.tnt.run.spawn;

import me.fixeddev.inject.ProtectedModule;

public class SpawnModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(PlayerSpawner.class).to(CorePlayerSpawner.class);
    }

}
