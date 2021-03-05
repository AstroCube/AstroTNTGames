package net.astrocube.tnt.run.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.map.configuration.CoordinatePoint;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Singleton
public class CorePlayerSpawner implements PlayerSpawner {

    private @Inject MessageHandler messageHandler;
    private @Inject
    Plugin plugin;

    @Override
    public void spawn(Player player, String match, CoordinatePoint point) {

        World world = Bukkit.getWorld("match_" + match);

        if (world == null) {
            Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(match, false));
            return;
        }

        Location location = new Location(world, point.getX(), point.getY(), point.getZ());

        Bukkit.getScheduler().runTask(plugin, () -> player.teleport(location));
    }

    @Override
    public void announce(Player player) {
        messageHandler.send(player, "game.help");
    }

}
