package net.astrocube.tnt.run.game;

import com.google.inject.Singleton;
import net.astrocube.tnt.perk.PerkProvider;
import org.bukkit.entity.Player;

@Singleton
public class CoreDoubleJumpProvider implements PerkProvider {

    @Override
    public int getPlayerPerk(Player player) {
        return 3;
    }

}
