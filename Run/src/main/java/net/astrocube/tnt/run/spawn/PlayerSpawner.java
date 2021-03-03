package net.astrocube.tnt.run.spawn;

import net.astrocube.api.bukkit.game.map.configuration.CoordinatePoint;
import org.bukkit.entity.Player;

import java.util.Set;

public interface PlayerSpawner {

    /**
     * Performs spawn for every player at
     * configured location.
     * @param player to spawn
     * @param match of the requesting world
     * @param point of the spawn
     */
    void spawn(Player player, String match, CoordinatePoint point);

    /**
     * Displays help tip at beginning for the user
     * @param player to announce
     */
    void announce(Player player);

}
