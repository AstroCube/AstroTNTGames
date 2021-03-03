package net.astrocube.tnt.run.spawn;

import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.map.configuration.CoordinatePoint;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Set;

@Singleton
public class CorePlayerSpawner implements PlayerSpawner {

    @Override
    public void spawn(Set<Player> players, String match, CoordinatePoint point) {

        World world = Bukkit.getWorld("match_" + match);

        if (world == null) {
            Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(match, false));
            return;
        }

        Location location = new Location(world, point.getX(), point.getY(), point.getZ());

        players.forEach(player -> player.teleport(location));

    }
}
