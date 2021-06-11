package net.astrocube.tnt.run.floor;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface FloorRemover {

    void removeFloor(Location location);

    default void removeFloorTo(Player player) {
        removeFloor(player.getLocation());
    }

}
