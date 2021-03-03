package net.astrocube.tnt.run.map;

import net.astrocube.api.bukkit.game.map.configuration.CoordinatePoint;
import net.astrocube.api.bukkit.game.map.configuration.GameMapConfiguration;
import net.astrocube.api.core.virtual.gamemode.GameMode;

public interface LayoutMapConfiguration extends GameMapConfiguration {

    /**
     * @return center of map where players will spawn.
     */
    CoordinatePoint getSpawn();

}
