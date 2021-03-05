package net.astrocube.tnt.run.loader.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.tnt.run.listener.*;
import org.bukkit.plugin.Plugin;

public class PlayerListenerLoader implements ListenerLoader {

    private @Inject Plugin plugin;

    private @Inject PlayerDamageListener playerDamageListener;
    private @Inject PlayerDisqualificationListener playerDisqualificationListener;
    private @Inject DoubleJumpListener doubleJumpListener;

    @Override
    public void registerEvents() {
        registerEvent(
                plugin,
                playerDamageListener,
                playerDisqualificationListener,
                doubleJumpListener
        );
    }


}
