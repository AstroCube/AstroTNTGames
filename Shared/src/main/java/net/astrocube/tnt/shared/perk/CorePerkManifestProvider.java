package net.astrocube.tnt.shared.perk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.perk.StorablePerk;
import net.astrocube.api.core.virtual.perk.StorablePerkDoc;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;

@Singleton
public class CorePerkManifestProvider implements PerkManifestProvider {

    private @Inject CreateService<StorablePerk, StorablePerkDoc.Partial> createService;
    private @Inject QueryService<StorablePerk> queryService;
    private @Inject UpdateService<StorablePerk, StorablePerkDoc.Partial> updateService;

    private @Inject ObjectMapper mapper;

    @Override
    public void createRegistry(String playerId, String gameMode) throws Exception {

        createService.createSync(new StorablePerkDoc.Creation() {
            @Override
            public String getGameMode() {
                return gameMode;
            }

            @Nullable
            @Override
            public String getSubGameMode() { return null; }

            @Override
            public String getResponsible() {
                return playerId;
            }

            @Override
            public String getType() {
                return "tnt_games_manifest";
            }

            @Override
            public Object getStored() {
                return new TNTPerkManifest() {
                    @Override
                    public int getMoney() {
                        return 0;
                    }

                    @Override
                    public void setMoney(int money) {}

                    @Override
                    public DoubleJumpTier getRunJumpTier() {
                        return DoubleJumpTier.I;
                    }

                    @Override
                    public void setRunJumpTier(DoubleJumpTier tier) {}

                    @Override
                    public DoubleJumpTier getSpleefJumpTier() {
                        return DoubleJumpTier.I;
                    }

                    @Override
                    public void setSpleefJumpTier(DoubleJumpTier tier) {}

                    @Override
                    public TripleShotTier getSpleefTripleShot() {
                        return TripleShotTier.I;
                    }

                    @Override
                    public void setSpleefTripleShot(TripleShotTier tier) {}
                };
            }

            @Override
            public void setStored(Object updatable) {}
        });

    }

    @Override
    public Optional<TNTPerkManifest> getManifest(String playerId) throws Exception {
        return obtainFromUser(playerId)
                .stream()
                .map(manifest -> (TNTPerkManifest) manifest.getStored())
                .findAny();
    }

    @Override
    public void update(String playerId, TNTPerkManifest manifest) throws Exception {

        StorablePerk perk = obtainFromUser(playerId).stream().findAny().orElseThrow(
                () -> new GameControlException("Not found any storable perk to be updated.")
        );

        perk.setStored(manifest);
        updateService.update(perk);

    }

    private Set<StorablePerk> obtainFromUser(String player) throws Exception {

        ObjectNode node = mapper.createObjectNode();

        node.put("responsible", player);
        node.put("type", "tnt_games_manifest");

        return queryService.querySync(node)
                .getFoundModels();
    }
}
