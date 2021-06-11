package net.astrocube.tnt.map;

import net.astrocube.api.bukkit.game.map.configuration.CoordinatePoint;
import net.astrocube.api.bukkit.game.map.configuration.GameMapConfiguration;

public interface MapConfiguration extends GameMapConfiguration {

	/**
	 * @return center of map where players will spawn.
	 */
	CoordinatePoint getSpawn();

}
