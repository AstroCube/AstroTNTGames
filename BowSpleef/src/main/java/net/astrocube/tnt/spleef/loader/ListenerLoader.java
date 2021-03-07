package net.astrocube.tnt.spleef.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.tnt.loader.CommonListenerLoader;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class ListenerLoader implements Loader {

    private @Inject Plugin plugin;

    private @Inject CommonListenerLoader commonListenerLoader;

    @Override
    public void load() {
        plugin.getLogger().log(Level.INFO, "Initializing event listeners");
        commonListenerLoader.registerEvents();
    }

}
