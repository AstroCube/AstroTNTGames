package net.astrocube.tnt.game;

import org.bukkit.entity.Player;

public interface DoubleJumpProvider {

    /**
     * Retrieve actual quantity of player jumps
     * @param player to retrieve
     * @return quantity of available jumps.
     */
    int getPlayerJumps(Player player);

}
