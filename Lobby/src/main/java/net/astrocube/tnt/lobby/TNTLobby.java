package net.astrocube.tnt.lobby;

import me.fixeddev.inject.ProtectedBinder;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.tnt.lobby.loader.InjectionLoaderModule;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;

public class TNTLobby extends JavaPlugin {

    private @Inject Loader loader;

    @Override
    public void onEnable() {
        loader.load();
    }

    @Override
    public void configure(ProtectedBinder binder) {
        binder.install(new InjectionLoaderModule());
    }

}
