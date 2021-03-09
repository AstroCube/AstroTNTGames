package net.astrocube.tnt.lobby.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.event.LobbyJoinEvent;
import net.astrocube.tnt.lobby.statistic.LobbyScoreboardProvider;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class GameLobbyJoinListener implements Listener {

    private @Inject LobbyScoreboardProvider lobbyScoreboardProvider;
    private @Inject Plugin plugin;

    @EventHandler
    public void onLobbyJoin(LobbyJoinEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> lobbyScoreboardProvider.setup(event.getPlayer()));
    }

}
