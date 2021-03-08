package net.astrocube.tnt.spleef.game;

import net.astrocube.tnt.game.DoubleJumpProvider;
import org.bukkit.entity.Player;

public class CoreDoubleJumpProvider implements DoubleJumpProvider {

    @Override
    public int getPlayerJumps(Player player) {
        return 3;
    }

}
