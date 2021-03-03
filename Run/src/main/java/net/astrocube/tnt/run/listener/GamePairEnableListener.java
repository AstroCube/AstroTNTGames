package net.astrocube.tnt.run.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.game.GameModePairEvent;
import net.astrocube.api.bukkit.game.event.game.GamePairEnableEvent;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.tnt.run.floor.FloorRemovalTask;
import net.astrocube.tnt.run.floor.FloorRemover;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class GamePairEnableListener implements Listener {

    private @Inject Plugin plugin;
    private @Inject FloorRemover floorRemover;
    private @Inject ActualMatchCache actualMatchCache;

    @EventHandler
    public void onGameReady(GamePairEnableEvent event) {

        Bukkit.getPluginManager().callEvent(
                new GameModePairEvent(
                        plugin.getConfig().getString("centauri.mode"),
                        plugin.getConfig().getString("centauri.subMode"))
        );

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,
                new FloorRemovalTask(actualMatchCache, floorRemover, plugin), 0L, 1L);

    }

}
