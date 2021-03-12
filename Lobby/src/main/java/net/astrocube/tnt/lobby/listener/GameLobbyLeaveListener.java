package net.astrocube.tnt.lobby.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.board.ScoreboardManagerProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import team.unnamed.uboard.ScoreboardObjective;

import java.util.Optional;

public class GameLobbyLeaveListener implements Listener {

    private @Inject ScoreboardManagerProvider scoreboardManagerProvider;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Optional<ScoreboardObjective> objectiveOptional =
                scoreboardManagerProvider.getScoreboard().getScoreboard(
                        "tntlobby_" + event.getPlayer().getDatabaseIdentifier()
                );

        objectiveOptional.ifPresent(objective -> scoreboardManagerProvider.getScoreboard().removeScoreboard(objective));

    }

}
