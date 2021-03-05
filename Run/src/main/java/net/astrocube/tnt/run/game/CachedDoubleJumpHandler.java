package net.astrocube.tnt.run.game;

import org.bukkit.entity.Player;

public interface CachedDoubleJumpHandler {

    /**
     * Register jump registry.
     * @param player to register
     */
    void registerJumps(Player player);

    /**
     * Removes one jump from user registry.
     * @param player to check
     */
    void useJump(Player player);

    /**
     * Check if user has remaining jumps
     * @param player to check
     * @return if player has remaining jumps
     */
    boolean hasRemainingJumps(Player player);

    /**
     * Get user remaining jumps
     * @param player to check
     * @return if player has remaining jumps
     */
    int getRemainingJumps(Player player);

    /**
     * Clear actual jumps
     * @param player to check
     */
    void clearJumps(Player player);

}
