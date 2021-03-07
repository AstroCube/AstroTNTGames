package net.astrocube.tnt.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.tnt.event.PlayerDisqualificationEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

public class PlayerDamageListener implements Listener {

    private @Inject ActualMatchCache actualMatchCache;
    private @Inject Plugin plugin;

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        if (!event.isCancelled()) {
            if (!(event.getEntity() instanceof Player)) {
                return;
            }

            Player player = (Player) event.getEntity();

            if (event.getCause() != EntityDamageEvent.DamageCause.VOID) {
                event.setCancelled(true);
                return;
            }

            try {

                Optional<Match> match = actualMatchCache.get(player.getDatabaseIdentifier());

                if (!match.isPresent() || !MatchParticipantsProvider.getOnlineIds(match.get()).contains(player.getDatabaseIdentifier())) {
                    event.setCancelled(true);
                    return;
                }

                Bukkit.getPluginManager().callEvent(new PlayerDisqualificationEvent(player, match.get().getId()));
                event.setCancelled(true);

            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "There was an error while logging error.");
                event.setCancelled(true);
            }
        }

    }

}
