package net.astrocube.tnt.perk;

import com.google.inject.Singleton;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CoreCachedPerkHandler implements CachedPerkHandler {

    private final PerkProvider perkProvider;
    private final Map<String, Integer> cachedPerk = new HashMap<>();

    public CoreCachedPerkHandler(PerkProvider provider) {
        this.perkProvider = provider;
    }

    @Override
    public void registerJumps(Player player) {

        int base;

        base = perkProvider.getPlayerPerk(player);

        cachedPerk.put(player.getDatabaseIdentifier(), base);

    }

    @Override
    public void usePerk(Player player) {
        int update = getRemainingUses(player);
        cachedPerk.put(player.getDatabaseIdentifier(), update - 1);
    }

    @Override
    public boolean hasRemainingUses(Player player) {
        return getRemainingUses(player) > 0;
    }

    @Override
    public int getRemainingUses(Player player) {
        return cachedPerk.get(player.getDatabaseIdentifier());
    }

    @Override
    public void clearUses(Player player) {
        cachedPerk.remove(player.getDatabaseIdentifier());
    }
}
