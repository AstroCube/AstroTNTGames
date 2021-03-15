package net.astrocube.tnt.lobby.menu;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.api.bukkit.perk.PerkManifestProvider;
import net.astrocube.tnt.shared.perk.TNTPerkManifest;
import net.astrocube.tnt.shared.perk.TNTPerkProvider;
import net.astrocube.tnt.shared.perk.configuration.PerkConfiguration;
import net.astrocube.tnt.shared.perk.configuration.PerkConfigurationCache;
import org.bukkit.entity.Player;
import team.unnamed.gui.core.gui.GUIBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UpgradeShopMenu {

    private @Inject MessageHandler messageHandler;
    private @Inject TNTMenuHelper tntMenuHelper;
    private @Inject PerkConfigurationCache perkConfigurationCache;
    private @Inject TNTPerkProvider tntPerkProvider;

    public void open(Player player, int money, PerkConfiguration.Purchasable.Type type) {

        GUIBuilder builder = GUIBuilder.builder(
                messageHandler.get(player, "child.run.title")
        );

        tntMenuHelper.addDefaultButtons(
                builder,
                player,
                money,
                (p) -> { }
        );

        try {

            TNTPerkManifest manifest = tntPerkProvider.getManifest(player.getDatabaseIdentifier()).orElseThrow(() ->
                    new GameControlException("Manifest not found."));

            List<PerkConfiguration.Purchasable> purchasableList = perkConfigurationCache.getCachedItems().stream()
                    .filter(purchasable -> purchasable.getType() == type)
                    .sorted(Comparator.comparing(PerkConfiguration.Purchasable::getOrder))
                    .collect(Collectors.toList());

            boolean active = false;
            for (PerkConfiguration.Purchasable purchasable : purchasableList) {



            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
