package net.astrocube.tnt.lobby.menu;

import me.fixeddev.inject.ProtectedModule;

public class MenuModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(MainShopMenu.class).to(CoreMainShopMenu.class);
    }

}
