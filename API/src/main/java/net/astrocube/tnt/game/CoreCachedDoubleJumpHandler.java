package net.astrocube.tnt.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class CoreCachedDoubleJumpHandler implements CachedDoubleJumpHandler {

    private @Inject DoubleJumpProvider doubleJumpProvider;
    private final Map<String, Integer> cachedJump = new HashMap<>();

    @Override
    public void registerJumps(Player player) {

        int base;

        base = doubleJumpProvider.getPlayerJumps(player);

        cachedJump.put(player.getDatabaseIdentifier(), base);

    }

    @Override
    public void useJump(Player player) {
        int update = getRemainingJumps(player);
        cachedJump.put(player.getDatabaseIdentifier(), update - 1);
    }

    @Override
    public boolean hasRemainingJumps(Player player) {
        return getRemainingJumps(player) > 0;
    }

    @Override
    public int getRemainingJumps(Player player) {
        return cachedJump.getOrDefault(player.getDatabaseIdentifier(), 3);
    }

    @Override
    public void clearJumps(Player player) {
        cachedJump.remove(player.getDatabaseIdentifier());
    }
}
