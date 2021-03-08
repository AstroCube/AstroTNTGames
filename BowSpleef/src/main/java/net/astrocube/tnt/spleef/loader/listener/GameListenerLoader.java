package net.astrocube.tnt.spleef.loader.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.tnt.spleef.listener.GamePairEnableListener;
import net.astrocube.tnt.spleef.listener.ProjectileLaunchListener;
import net.astrocube.tnt.spleef.listener.TripleShotListener;
import org.bukkit.plugin.Plugin;

public class GameListenerLoader implements ListenerLoader {

    private @Inject Plugin plugin;

    private @Inject TripleShotListener tripleShotListener;
    private @Inject GamePairEnableListener gamePairEnableListener;
    private @Inject ProjectileLaunchListener projectileLaunchListener;

    @Override
    public void registerEvents() {
        registerEvent(
                plugin,
                tripleShotListener,
                gamePairEnableListener,
                projectileLaunchListener
        );
    }


}
