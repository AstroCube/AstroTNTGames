package net.astrocube.tnt.run.loader.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.tnt.run.listener.GamePairEnableListener;
import net.astrocube.tnt.run.listener.GameReadyListener;
import net.astrocube.tnt.run.listener.PlayerDamageListener;
import net.astrocube.tnt.run.listener.PlayerDisqualificationListener;
import org.bukkit.plugin.Plugin;

public class PlayerListenerLoader implements ListenerLoader {

    private @Inject Plugin plugin;

    private @Inject PlayerDamageListener playerDamageListener;
    private @Inject PlayerDisqualificationListener playerDisqualificationListener;

    @Override
    public void registerEvents() {
        registerEvent(
                plugin,
                playerDamageListener,
                playerDisqualificationListener
        );
    }


}
