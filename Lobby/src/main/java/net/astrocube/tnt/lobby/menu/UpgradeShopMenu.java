package net.astrocube.tnt.lobby.menu;

import com.google.inject.Inject;
import lombok.AllArgsConstructor;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.api.bukkit.menu.MenuUtils;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.tnt.shared.perk.TNTPerkManifest;
import net.astrocube.tnt.shared.perk.TNTPerkProvider;
import net.astrocube.tnt.shared.perk.configuration.PerkConfiguration;
import net.astrocube.tnt.shared.perk.configuration.PerkConfigurationCache;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class UpgradeShopMenu {

    private @Inject MessageHandler messageHandler;
    private @Inject TNTMenuHelper tntMenuHelper;
    private @Inject PerkConfigurationCache perkConfigurationCache;
    private @Inject TNTPerkProvider tntPerkProvider;
    private @Inject GenericHeadHelper genericHeadHelper;
    private @Inject Plugin plugin;

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

            TNTPerkManifest manifest = tntPerkProvider
                    .getManifest(player.getDatabaseIdentifier())
                    .orElseThrow(() -> new GameControlException("Manifest not found."));

            String typeSort = "";

            switch (type) {
                case SPLEEF_JUMP: {
                    typeSort = manifest.getSpleefJumpTier();
                    break;
                }
                case RUN_JUMP: {
                    typeSort = manifest.getRunJumpTier();
                    break;
                }
                case SPLEEF_SHOT: {
                    typeSort = manifest.getSpleefTripleShot();
                    break;
                }
                default: {
                    break;
                }
            }

            List<PerkConfiguration.Purchasable> purchasableList = perkConfigurationCache.getCachedItems().stream()
                    .filter(purchasable -> purchasable.getType() == type)
                    .sorted(Comparator.comparing(PerkConfiguration.Purchasable::getOrder))
                    .collect(Collectors.toList());

            boolean active = false;
            boolean upgradable = false;
            int index = 19;
            for (PerkConfiguration.Purchasable purchasable : purchasableList) {

                System.out.println("Starting translation");
                System.out.println("Purchasable: " + purchasable.getName());

                while (MenuUtils.isMarkedSlot(index)) {
                    index++;
                }

                if (!active) {

                    if (purchasable.getName().equalsIgnoreCase(typeSort)) {
                        active = true;
                    }

                    System.out.println("Status: OWNED");

                    addToGUI(
                            builder,
                            generatePurchasableGlass(
                                    purchasable,
                                    player,
                                    PurchasableGlass.OWNED
                            ),
                            index
                    );

                    index++;
                    continue;

                }

                if (!upgradable) {

                    upgradable = true;

                    if (purchasable.getPrice() > manifest.getMoney()) {

                        addToGUI(
                                builder,
                                generatePurchasableGlass(
                                        purchasable,
                                        player,
                                        PurchasableGlass.MONEY
                                ),
                                index
                        );

                        System.out.println("Status: UNAVAILABLE-MONEY");

                    } else {

                        addToGUI(
                                builder,
                                generatePurchasableGlass(
                                        purchasable,
                                        player,
                                        PurchasableGlass.NEXT
                                ),
                                index
                        );


                        System.out.println("Status: NEXT");

                    }

                    index++;
                    continue;

                }

                addToGUI(
                        builder,
                        generatePurchasableGlass(
                                purchasable,
                                player,
                                PurchasableGlass.INSUFFICIENT
                        ),
                        index
                );


                System.out.println("Status: INSUFFICIENT PERKS");

                index++;

            }

            player.openInventory(builder.build());

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error while opening upgrade shop menu", e);
        }

    }

    private void addToGUI(GUIBuilder builder, ShapedMenuGenerator.BaseClickable baseClickable, int slot) {
        builder.addItem(
                ItemClickable.builder(slot)
                .setItemStack(baseClickable.getStack())
                .setAction(event ->  {
                    baseClickable.getClick().accept((Player) event.getWhoClicked());
                    return true;
                }).build()
        );
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
