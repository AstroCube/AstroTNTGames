package net.astrocube.tnt.spleef.projectile;

import org.bukkit.entity.Player;

public interface ProjectileCompoundMatcher {

    /**
     * @param player to retrieve
     * @return compound to use
     */
    ProjectileCompound getChosenCompound(Player player);

}
