package net.astrocube.tnt.lobby.menu.category;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.tnt.lobby.menu.CoreUpgradeShopMenu;
import net.astrocube.tnt.lobby.menu.MainShopMenu;
import net.astrocube.tnt.lobby.menu.TNTMenuHelper;
import net.astrocube.tnt.lobby.menu.UpgradeShopMenu;
import net.astrocube.tnt.shared.perk.configuration.PerkConfiguration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.core.gui.GUIBuilder;

@Singleton
public class SpleefMenu implements MainShopMenu.SubMenu {

    private @Inject TNTMenuHelper tntMenuHelper;
    private @Inject MessageHandler messageHandler;
    private @Inject MainShopMenu mainShopMenu;
    private @Inject GenericHeadHelper genericHeadHelper;
    private @Inject UpgradeShopMenu upgradeShopMenu;

    @Override
    public void open(Player player, int money) {

        GUIBuilder builder = GUIBuilder.builder(
                messageHandler.get(player, "child.spleef.title")
        );

        tntMenuHelper.addDefaultButtons(
                builder,
                player,
                money,
                (p) -> mainShopMenu.open(player)
        );


        ItemStack jumpIcon = genericHeadHelper.generateMetaAndPlace(
                new ItemStack(Material.DIAMOND_BOOTS),
                messageHandler.get(player, "child.spleef.double-jump.title"),
                messageHandler.getMany(player, "child.spleef.double-jump.lore")
        );

        builder.addItem(
                genericHeadHelper.generateDefaultClickable(
                        jumpIcon,
                        20,
                        ClickType.LEFT,
                        (p) -> upgradeShopMenu.open(
                                p,
                                money,
                                PerkConfiguration.Purchasable.Type.SPLEEF_JUMP,
                                jumpIcon,
                                (back) -> open(
                                        back,
                                        money
                                )
                        )
                )
        );

        ItemStack tripleShotIcon = genericHeadHelper.generateMetaAndPlace(
                new ItemStack(Material.ARROW),
                messageHandler.get(player, "child.spleef.double-jump.title"),
                messageHandler.getMany(player, "child.spleef.double-jump.lore")
        );

        builder.addItem(
                genericHeadHelper.generateDefaultClickable(
                        tripleShotIcon,
                        24,
                        ClickType.LEFT,
                        (p) -> upgradeShopMenu.open(
                                p,
                                money,
                                PerkConfiguration.Purchasable.Type.SPLEEF_SHOT,
                                tripleShotIcon,
                                (back) -> open(back, money)
                        )
                )
        );

        player.openInventory(builder.build());

    }

}
