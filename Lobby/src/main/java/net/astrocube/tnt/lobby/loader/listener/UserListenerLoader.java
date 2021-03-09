package net.astrocube.tnt.lobby.loader.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.tnt.lobby.listener.GameLobbyJoinListener;
import org.bukkit.plugin.Plugin;

public class UserListenerLoader implements ListenerLoader {

    private @Inject Plugin plugin;
    private @Inject GameLobbyJoinListener gameLobbyJoinListener;

    @Override
    public void registerEvents() {
        registerEvent(
                plugin,
                gameLobbyJoinListener
        );
    }
}
