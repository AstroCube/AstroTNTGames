package net.astrocube.tnt.run.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.game.GameReadyEvent;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.event.match.MatchStartEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.MapConfigurationProvider;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.tnt.run.floor.FloorCooldownChecker;
import net.astrocube.tnt.run.game.ScoreboardProvider;
import net.astrocube.tnt.run.map.MapConfiguration;
import net.astrocube.tnt.run.game.PlayerSpawner;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class GameReadyListener implements Listener {

    private @Inject FindService<Match> findService;
    private @Inject FloorCooldownChecker floorCooldownChecker;

    private @Inject MapConfigurationProvider mapConfigurationProvider;
    private @Inject Plugin plugin;

    private @Inject PlayerSpawner playerSpawner;
    private @Inject ScoreboardProvider scoreboardProvider;

    @EventHandler
    public void onGameReady(GameReadyEvent event) {

        findService.find(event.getMatch()).callback(response -> {

            try {

                if (!response.isSuccessful()) {
                    throw new GameControlException("The requested match was not found");
                }

                MapConfiguration configuration =
                        mapConfigurationProvider.parseConfiguration(event.getConfiguration(), MapConfiguration.class);

                floorCooldownChecker.scheduleCooldown(event.getMatch());

                response.ifSuccessful(match -> {

                    event.getTeams().stream()
                            .flatMap(team ->
                                    team.getMembers().stream().map(MatchDoc.TeamMember::getUser)
                            )
                            .forEach(player -> {

                                Player online = Bukkit.getPlayerByIdentifier(player);

                                if (online != null) {

                                    playerSpawner.spawn(online, match.getId(), configuration.getSpawn());
                                    playerSpawner.announce(online);
                                    scoreboardProvider.setupBoard(online);

                                }

                            });

                    Bukkit.getPluginManager().callEvent(new MatchStartEvent(match.getId()));

                });

            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "There was an error processing game ready event.", e);
                Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(event.getMatch(),false));
            }

        });

    }

}
