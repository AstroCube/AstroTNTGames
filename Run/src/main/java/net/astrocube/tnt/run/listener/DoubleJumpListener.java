package net.astrocube.tnt.run.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.tnt.run.game.CachedDoubleJumpHandler;
import net.astrocube.tnt.run.game.ScoreboardProvider;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Optional;
import java.util.logging.Level;

public class DoubleJumpListener implements Listener {

    private @Inject ActualMatchCache actualMatchCache;
    private @Inject CachedDoubleJumpHandler cachedDoubleJumpHandler;
    private @Inject ScoreboardProvider scoreboardProvider;
    private @Inject Plugin plugin;

    @EventHandler
    public void onFlightToggled(PlayerToggleFlightEvent event) {

        if (event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getPlayer().getGameMode () == GameMode.SPECTATOR) {
            return;
        }

        Player player = event.getPlayer();

        try {
            Optional<Match> optionalMatch = actualMatchCache.get(player.getDatabaseIdentifier());

            optionalMatch.ifPresent(match -> {

                if (!MatchParticipantsProvider.getOnlinePlayers(match).contains(player)) {
                    return;
                }

                if (!cachedDoubleJumpHandler.hasRemainingJumps(player)) {
                    event.setCancelled(true);
                    player.setAllowFlight(false);
                    return;
                }


                event.setCancelled(true);
                event.getPlayer().setAllowFlight(false);
                event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(1.4d).setY(1.0d));

                cachedDoubleJumpHandler.useJump(player);
                scoreboardProvider.setupBoard(player);

                if (cachedDoubleJumpHandler.hasRemainingJumps(player)) {
                    player.setAllowFlight(true);
                }

            });

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error obtaining match assignation", e);
        }


    }

}
