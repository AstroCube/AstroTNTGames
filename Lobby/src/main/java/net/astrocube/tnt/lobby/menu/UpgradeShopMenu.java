package net.astrocube.tnt.lobby.menu;

import com.google.inject.Inject;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.AllArgsConstructor;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.bukkit.perk.PerkManifestProvider;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.tnt.shared.perk.TNTPerkManifest;
import net.astrocube.tnt.shared.perk.TNTPerkProvider;
import net.astrocube.tnt.shared.perk.configuration.PerkConfiguration;
import net.astrocube.tnt.shared.perk.configuration.PerkConfigurationCache;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.core.gui.GUIBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class UpgradeShopMenu {

    private @Inject MessageHandler messageHandler;
    private @Inject TNTMenuHelper tntMenuHelper;
    private @Inject PerkConfigurationCache perkConfigurationCache;
    private @Inject TNTPerkProvider tntPerkProvider;
    private @Inject GenericHeadHelper genericHeadHelper;

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
            boolean upgradable = false;
            for (PerkConfiguration.Purchasable purchasable : purchasableList) {

                if (!active) {

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AllArgsConstructor
    private enum PurchasableGlass {
        OWNED("upgrade.action.owned", (short) 5),
        NEXT("upgrade.action.next", (short) 4),
        MONEY("upgrade.action.money", (short) 14),
        INSUFFICIENT("upgrade.action.insufficient", (short) 14);

        private final String translation;
        private final short color;

        public String getTranslation() {
            return translation;
        }

        public short getColor() {
            return color;
        }

    }

    private ShapedMenuGenerator.BaseClickable generatePurchasableGlass(PerkConfiguration.Purchasable purchasable, Player player, PurchasableGlass glassType) {

        StringList list = messageHandler.getMany(player, purchasable.getType().getTranslation() + ".lore");

        String translation = messageHandler.get(player, glassType.getTranslation());

        list.add(translation);

        ItemStack stack = genericHeadHelper.generateMetaAndPlace(
                new ItemStack(Material.STAINED_GLASS_PANE, 1, glassType.getColor()),
                messageHandler.get(
                        player,
                        purchasable.getType().getTranslation() + ".title." + purchasable.getName().toLowerCase(Locale.ROOT)
                ),
                list
        );

        return new ShapedMenuGenerator.BaseClickable() {
            @Override
            public Consumer<Player> getClick() {
                return (p) -> {

                    switch (glassType) {
                        case NEXT: {
                            //TODO: Create money exchange
                            break;
                        }
                        case MONEY: {
                            messageHandler.sendIn(player, AlertModes.ERROR, "upgrade.click.money");
                            break;
                        }
                        case OWNED: {
                            messageHandler.sendIn(player, AlertModes.INFO, "upgrade.click.owned");
                            break;
                        }
                        default: {
                            messageHandler.sendIn(player, AlertModes.INFO, "upgrade.click.insufficient");
                            break;
                        }
                    }


                    p.closeInventory();

                };
            }

            @Override
            public ItemStack getStack() {
                return stack;
            }
        };

    }


}
