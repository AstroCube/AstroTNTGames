package net.astrocube.tnt.lobby.menu;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.core.gui.GUIBuilder;

import java.util.ArrayList;

public class CoreUpgradeConfirmationMenu implements UpgradeConfirmationMenu {

    private @Inject MessageHandler messageHandler;
    private @Inject GenericHeadHelper genericHeadHelper;

    @Override
    public void open(Player player, Runnable confirm, Runnable reject, String upgrade) {

        GUIBuilder builder = GUIBuilder.builder(messageHandler.get(player, "upgrade.confirm.title"),3);

        builder.addItem(
                genericHeadHelper.generateDefaultClickable(
                        genericHeadHelper.generateMetaAndPlace(
                                new ItemStack(Material.STAINED_CLAY, 13),
                                messageHandler.replacing(
                                        player, "upgrade.confirm.confirm",
                                        "%%upgrade%%", upgrade
                                ),
                                new ArrayList<>()
                        ),
                        10,
                        ClickType.LEFT,
                        (p) -> {
                            if (confirm != null) {
                                confirm.run();
                            }
                        }
                )
        );

        builder.addItem(
                genericHeadHelper.generateDefaultClickable(
                        genericHeadHelper.generateMetaAndPlace(
                                new ItemStack(Material.STAINED_CLAY, 14),
                                messageHandler.get(player, "upgrade.confirm.cancel"),
                                new ArrayList<>()
                        ),
                        10,
                        ClickType.LEFT,
                        (p) -> {
                            if (reject != null) {
                                reject.run();
                            }
                        }
                )
        );

        player.openInventory(builder.build());

    }
}
