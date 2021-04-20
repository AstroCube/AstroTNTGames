package net.astrocube.tnt.run.listener;

import com.google.inject.Inject;
import net.astrocube.tnt.event.PlayerDisqualificationEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class PlayerDisqualificationStatusListener implements Listener {

    @Inject
    private Plugin plugin;

    @EventHandler
    public void onPlayerDisqualification(PlayerDisqualificationEvent event) {
        event.getPlayer().removeMetadata("playing", plugin);
    }

}
