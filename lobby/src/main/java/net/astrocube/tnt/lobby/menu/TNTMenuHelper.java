package net.astrocube.tnt.lobby.menu;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.function.Consumer;

public class TNTMenuHelper {

	private @Inject GenericHeadHelper genericHeadHelper;
	private @Inject MessageHandler messageHandler;
	private @Inject NumberFormat numberFormat;

	public void addDefaultButtons(GUIBuilder builder, Player player, int money, Runnable back) {
		builder.addItem(genericHeadHelper.generateItemCancellingEvent(
				ItemBuilder.newBuilder(Material.DIAMOND)
						.setName(messageHandler.replacing(
								player, "store.percentage.title",
								"%money%", numberFormat.format(money)
						))
						.setLore(messageHandler.getMany(player, "store.percentage.lore"))
						.build(),
				49,
				ClickType.LEFT
		)).addItem(genericHeadHelper.generateItem(
				genericHeadHelper.generateMetaAndPlace(
						genericHeadHelper.backButton(player),
						messageHandler.get(player, "store.close"),
						new ArrayList<>()
				),
				48,
				ClickType.LEFT,
				back
		));
	}

}
