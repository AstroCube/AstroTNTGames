package net.astrocube.tnt.loader;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.tnt.listener.*;
import org.bukkit.plugin.Plugin;

public class CommonListenerLoader implements ListenerLoader {

    private @Inject Plugin plugin;

    private @Inject DoubleJumpListener doubleJumpListener;
    private @Inject
    PlayerActionsListener playerActionsListener;
    private @Inject PlayerDisqualificationListener playerDisqualificationListener;
    private @Inject SpectatorAssignListener spectatorAssignListener;
    private @Inject GameFinishListener gameFinishListener;

    @Override
    public void registerEvents() {
        registerEvent(
                plugin,
                doubleJumpListener,
                playerActionsListener,
                playerDisqualificationListener,
                gameFinishListener,
                spectatorAssignListener
        );
    }
}
