package net.astrocube.tnt.perk;

import org.bukkit.entity.Player;

public interface CachedPerkHandler {

    /**
     * Register perk registry.
     * @param player to register
     */
    void registerJumps(Player player);

    /**
     * Removes one perk from user registry.
     * @param player to check
     */
    void usePerk(Player player);

    /**
     * Check if user has remaining perks
     * @param player to check
     * @return if player has remaining perks
     */
    boolean hasRemainingUses(Player player);

    /**
     * Get user remaining perks
     * @param player to check
     * @return if player has remaining perks
     */
    int getRemainingUses(Player player);

    /**
     * Clear actual perks
     * @param player to check
     */
    void clearUses(Player player);

}
