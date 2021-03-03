package net.astrocube.tnt.run.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.game.GameReadyEvent;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class GameReadyListener implements Listener {

    private @Inject FindService<Match> findService;
    private @Inject Plugin plugin;

    @EventHandler
    public void onGameReady(GameReadyEvent event) {

        findService.find(event.getMatch()).callback(response -> {
            try {

                if (!response.isSuccessful() || !response.getResponse().isPresent()) {
                    throw new GameControlException("The requested match was not found");
                }



            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "There was an error processing game ready event.", e);
                Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(event.getMatch(),false));
            }

        });

    }

}
