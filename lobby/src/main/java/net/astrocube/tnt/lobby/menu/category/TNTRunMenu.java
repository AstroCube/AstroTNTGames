package net.astrocube.tnt.lobby.menu.category;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.tnt.lobby.menu.CoreUpgradeShopMenu;
import net.astrocube.tnt.lobby.menu.MainShopMenu;
import net.astrocube.tnt.lobby.menu.TNTMenuHelper;
import net.astrocube.tnt.shared.perk.configuration.PerkConfiguration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.core.gui.type.GUIBuilder;

@Singleton
public class TNTRunMenu implements MainShopMenu.SubMenu {

    private @Inject TNTMenuHelper tntMenuHelper;
    private @Inject MessageHandler messageHandler;
    private @Inject MainShopMenu mainShopMenu;
    private @Inject GenericHeadHelper genericHeadHelper;
    private @Inject
    CoreUpgradeShopMenu upgradeShopMenu;

    @Override
    public void open(Player player, int money) {

        GUIBuilder builder = GUIBuilder.builder(
                messageHandler.get(player, "child.run.title")
        );

        tntMenuHelper.addDefaultButtons(
                builder,
                player,
                money,
                (p) -> mainShopMenu.open(player)
        );

        ItemStack doubleJump = genericHeadHelper.generateMetaAndPlace(
                new ItemStack(Material.DIAMOND_BOOTS),
                messageHandler.get(player, "child.run.double-jump.title"),
                messageHandler.getMany(player, "child.run.double-jump.lore")
        );

        builder.addItem(
                genericHeadHelper.generateDefaultClickable(
                        doubleJump,
                        22,
                        ClickType.LEFT,
                        (p) -> upgradeShopMenu.open(
                                p, money, PerkConfiguration.Purchasable.Type.RUN_JUMP,
                                doubleJump,
                                (back) -> open(back, money)
                        )
                )
        );

        player.openInventory(builder.build());

    }

}
