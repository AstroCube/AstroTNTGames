package net.astrocube.tnt.spleef.game;

import net.astrocube.tnt.perk.PerkProvider;
import org.bukkit.entity.Player;

public class CoreTripleShotProvider implements PerkProvider {
    @Override
    public int getPlayerPerk(Player player) {
        return 3;
    }
}
