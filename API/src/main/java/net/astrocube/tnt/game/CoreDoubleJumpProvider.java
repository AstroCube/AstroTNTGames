package net.astrocube.tnt.game;

import com.google.inject.Singleton;
import org.bukkit.entity.Player;

@Singleton
public class CoreDoubleJumpProvider implements DoubleJumpProvider{

    @Override
    public int getPlayerJumps(Player player) {
        return 3;
    }

}
