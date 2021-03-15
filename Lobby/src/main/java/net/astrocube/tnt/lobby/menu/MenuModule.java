package net.astrocube.tnt.lobby.menu;

import com.google.inject.name.Names;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.tnt.lobby.menu.category.SpleefMenu;
import net.astrocube.tnt.lobby.menu.category.TNTRunMenu;

public class MenuModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(MainShopMenu.class).to(CoreMainShopMenu.class);
        bind(MainShopMenu.SubMenu.class).annotatedWith(Names.named("spleef")).to(SpleefMenu.class);
        bind(MainShopMenu.SubMenu.class).annotatedWith(Names.named("run")).to(TNTRunMenu.class);
    }

}
