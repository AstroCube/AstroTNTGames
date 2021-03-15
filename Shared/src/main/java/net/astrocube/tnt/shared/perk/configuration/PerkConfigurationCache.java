package net.astrocube.tnt.shared.perk.configuration;

import java.util.Set;

public interface PerkConfigurationCache {

    /**
     * Generate cache at server loading.
     */
    void generate();

    /**
     * @return cached items.
     */
    Set<PerkConfiguration.Purchasable> getCachedItems();

}
