package net.astrocube.tnt.lobby.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.event.LobbyJoinEvent;
import net.astrocube.tnt.lobby.statistic.LobbyScoreboardProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameLobbyJoinListener implements Listener {

    private @Inject LobbyScoreboardProvider lobbyScoreboardProvider;

    @EventHandler
    public void onLobbyJoin(LobbyJoinEvent event) {
        lobbyScoreboardProvider.setup(event.getPlayer());
    }

}
