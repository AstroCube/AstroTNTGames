package net.astrocube.tnt.spleef.loader.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.tnt.spleef.listener.*;
import org.bukkit.plugin.Plugin;

public class GameListenerLoader implements ListenerLoader {

    private @Inject Plugin plugin;

    private @Inject TripleShotListener tripleShotListener;
    private @Inject GamePairEnableListener gamePairEnableListener;
    private @Inject GameReadyListener gameReadyListener;
    private @Inject ProjectileLaunchListener projectileLaunchListener;
    private @Inject EntityExplodeListener entityExplodeListener;

    @Override
    public void registerEvents() {
        registerEvent(
                plugin,
                tripleShotListener,
                gameReadyListener,
                gamePairEnableListener,
                projectileLaunchListener,
                entityExplodeListener
        );
    }


}
