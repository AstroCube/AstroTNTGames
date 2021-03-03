package net.astrocube.tnt.run.floor;

import me.fixeddev.inject.ProtectedModule;

public class FloorModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(FloorCooldownChecker.class).to(CoreFloorCooldownChecker.class);
        bind(FloorRemover.class).to(CoreFloorRemover.class);
    }

}
