package net.astrocube.tnt.run.loader.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.tnt.run.listener.GameFinishListener;
import net.astrocube.tnt.run.listener.GamePairEnableListener;
import net.astrocube.tnt.run.listener.GameReadyListener;
import org.bukkit.plugin.Plugin;

public class GameListenerLoader implements ListenerLoader {

    private @Inject Plugin plugin;

    private @Inject GameReadyListener gameReadyListener;
    private @Inject GamePairEnableListener gamePairEnableListener;
    private @Inject GameFinishListener gameFinishListener;

    @Override
    public void registerEvents() {
        registerEvent(
                plugin,
                gameReadyListener,
                gamePairEnableListener,
                gameFinishListener
        );
    }


}
