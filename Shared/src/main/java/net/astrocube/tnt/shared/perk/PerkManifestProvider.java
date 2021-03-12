package net.astrocube.tnt.shared.perk;

import net.astrocube.api.core.virtual.perk.StorablePerk;
import org.bukkit.entity.Player;

import java.util.Optional;

public interface PerkManifestProvider {

    /**
     * Creates a perk manifest as an {@link StorablePerk}.
     * @param playerId to create
     * @param gameMode to store
     */
    void createRegistry(String playerId, String gameMode) throws Exception;

    /**
     * Retrieves perk manifest from {@link StorablePerk}.
     * @param playerId to retrieve
     * @return optional containing possible manifest
     */
    Optional<TNTPerkManifest> getManifest(String playerId) throws Exception;

    /**
     * updates a certain manifest according to player stored {@link StorablePerk}.
     * @param playerId to update
     * @param manifest to update
     */
    void update(String playerId, TNTPerkManifest manifest) throws Exception;

}
