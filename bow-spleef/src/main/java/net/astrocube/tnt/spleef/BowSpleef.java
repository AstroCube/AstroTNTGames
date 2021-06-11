package net.astrocube.tnt.spleef;

import com.google.inject.Inject;
import me.fixeddev.inject.ProtectedBinder;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.tnt.spleef.loader.InjectionLoaderModule;
import org.bukkit.plugin.java.JavaPlugin;

public class BowSpleef extends JavaPlugin {

    private @Inject Loader loader;

    @Override
    public void onEnable() {
        loader.load();
        copyDefaults();
    }

    @Override
    public void configure(ProtectedBinder binder) {
        binder.install(new InjectionLoaderModule());
    }

    public void copyDefaults() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

}