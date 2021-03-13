package net.astrocube.tnt.lobby.menu;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.api.bukkit.user.inventory.nbt.NBTUtils;
import net.astrocube.tnt.shared.perk.TNTPerkManifest;
import net.astrocube.tnt.shared.perk.TNTPerkProvider;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.core.gui.GUIBuilder;

import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreMainShopMenu implements MainShopMenu {

    private @Inject MessageHandler messageHandler;
    private @Inject GenericHeadHelper genericHeadHelper;
    private @Inject TNTPerkProvider tntPerkProvider;
    private @Inject Plugin plugin;

    @Override
    public void open(Player player) {

        int money = -1;
        try {
            TNTPerkManifest manifest = tntPerkProvider.getManifest(player.getDatabaseIdentifier())
                    .orElseThrow(() -> new GameControlException("Manifest not found"));

            money = manifest.getMoney();

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error opening menu for player", e);
        }

        GUIBuilder builder = GUIBuilder.builder(
                messageHandler.get(player, "store.title"),
                6
        );

        builder.addItem(
            genericHeadHelper.generateDefaultClickable(
                    genericHeadHelper.generateMetaAndPlace(
                            new ItemStack(Material.TNT),
                            messageHandler.get(player, "store.categories.run.title"),
                            messageHandler.getMany(player, "store.categories.run.lore")
                    ),
                    20,
                    ClickType.LEFT,
                    (p) -> {}
            )
        );

        builder.addItem(
                genericHeadHelper.generateDefaultClickable(
                        genericHeadHelper.generateMetaAndPlace(
                                new ItemStack(Material.BOW),
                                messageHandler.get(player, "store.categories.spleef.title"),
                                messageHandler.getMany(player, "store.categories.spleef.lore")
                        ),
                        24,
                        ClickType.LEFT,
                        (p) -> {}
                )
        );

        builder.addItem(
                genericHeadHelper.generateDefaultClickable(
                        genericHeadHelper.generateMetaAndPlace(
                                new ItemStack(Material.DIAMOND),
                                messageHandler.replacing(
                                        player, "store.percentage.title",
                                        "%%money%%",
                                        money
                                ),
                                messageHandler.getMany(player, "store.percentage.lore")
                        ),
                        49,
                        ClickType.LEFT,
                        (p) -> {}
                )
        );

        builder.addItem(
                genericHeadHelper.generateDefaultClickable(
                        genericHeadHelper.generateMetaAndPlace(
                                genericHeadHelper.backButton(player),
                                messageHandler.get(player, "store.close"),
                                new ArrayList<>()
                        ),
                        48,
                        ClickType.LEFT,
                        HumanEntity::closeInventory
                )
        );

        player.openInventory(builder.build());

    }

    @Override
    public ItemStack generateItem(Player player) {
        return genericHeadHelper.generateMetaAndPlace(
                NBTUtils.addString(
                        new ItemStack(Material.DIAMOND),
                        "actionable",
                        "tnt_shop"
                ),
                messageHandler.get(player, "store.item"),
                new ArrayList<>()
        );
    }

}
