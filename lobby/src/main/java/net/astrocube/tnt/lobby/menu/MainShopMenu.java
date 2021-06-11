package net.astrocube.tnt.lobby.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface MainShopMenu {

    /**
     * Open main shop menu for a player
     * @param player to open menu
     */
    void open(Player player);

    /**
     * Generate item to open
     * @param player to generate translation
     * @return translated actionable item
     */
    ItemStack generateItem(Player player);

    interface SubMenu {

        /**
         * Open category menu for a player
         * @param player to open menu.
         * @param money of the player
         */
        void open(Player player, int money);

    }

}
