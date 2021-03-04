package net.astrocube.tnt.run.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.tnt.run.loader.listener.GameListenerLoader;
import net.astrocube.tnt.run.loader.listener.PlayerListenerLoader;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class ListenerLoader implements Loader {

    private @Inject Plugin plugin;

    private @Inject GameListenerLoader gameListenerLoader;
    private @Inject PlayerListenerLoader playerListenerLoader;

    @Override
    public void load() {
        plugin.getLogger().log(Level.INFO, "Initializing event listeners");
        gameListenerLoader.registerEvents();
        playerListenerLoader.registerEvents();
    }

}
