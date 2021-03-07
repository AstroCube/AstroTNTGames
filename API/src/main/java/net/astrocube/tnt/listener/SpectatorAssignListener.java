package net.astrocube.tnt.listener;


import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.spectator.SpectatorAssignEvent;
import net.astrocube.tnt.game.ScoreboardProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SpectatorAssignListener implements Listener {

    private @Inject ScoreboardProvider scoreboardProvider;

    @EventHandler
    public void onFlightToggled(SpectatorAssignEvent event) {

        scoreboardProvider.setupBoard(event.getPlayer());

    }

}
