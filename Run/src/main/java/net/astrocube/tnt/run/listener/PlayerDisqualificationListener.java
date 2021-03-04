package net.astrocube.tnt.run.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.match.MatchFinishEvent;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.event.spectator.SpectatorAssignEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.tnt.event.PlayerDisqualificationEvent;
import net.astrocube.tnt.podium.MatchProgress;
import net.astrocube.tnt.podium.MatchProgressHandler;
import net.astrocube.tnt.run.game.ScoreboardProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class PlayerDisqualificationListener implements Listener {

    private @Inject ActualMatchCache actualMatchCache;
    private @Inject ScoreboardProvider scoreboardProvider;
    private @Inject MatchProgressHandler matchProgressHandler;
    private @Inject Plugin plugin;

    @EventHandler
    public void onPlayerDisqualification(PlayerDisqualificationEvent event) {

        try {

            Optional<Match> match = actualMatchCache.get(event.getPlayer().getDatabaseIdentifier());

            if (match.isPresent()) {

                Bukkit.getPluginManager().callEvent(new SpectatorAssignEvent(event.getPlayer(), match.get().getId()));

                Set<Player> players = MatchParticipantsProvider.getOnlinePlayers(match.get());
                players.forEach(p -> scoreboardProvider.setupBoard(p));
                matchProgressHandler.disqualify(match.get().getId(), event.getPlayer().getDatabaseIdentifier());

                MatchProgress progress = matchProgressHandler.getMatchProgress(match.get().getId())
                        .orElseThrow(() -> new GameControlException("Error obtaining match progress"));

                Set<MatchProgress.Participant> alive =
                        progress.getDisqualifiedPlayers()
                                .stream().filter(p -> p.getDisqualificationDate() == null).collect(Collectors.toSet());

                if (alive.size() <= 1) {
                    Bukkit.getPluginManager().callEvent(
                            new MatchFinishEvent(
                                    match.get().getId(),
                                    alive.stream().map(MatchProgress.Participant::getPlayerId).collect(Collectors.toSet())
                            )
                    );
                }

            }

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error during match disqualification.");
            Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(event.getMatch(), false));
        }


    }

}
