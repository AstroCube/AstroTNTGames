package net.astrocube.tnt.perk;

import org.bukkit.entity.Player;

public interface PerkProvider {

    /**
     * Retrieve actual quantity of available perk use.
     * @param player to retrieve
     * @return quantity of available perk.
     */
    int getPlayerPerk(Player player);

}
