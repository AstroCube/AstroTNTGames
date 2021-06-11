package net.astrocube.tnt.lobby.menu;

import com.google.inject.Inject;

import me.yushust.message.MessageHandler;

import net.astrocube.api.bukkit.menu.GenericHeadHelper;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.plugin.Plugin;

import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

public class CoreUpgradeConfirmationMenu implements UpgradeConfirmationMenu {

	private @Inject MessageHandler messageHandler;
	private @Inject GenericHeadHelper genericHeadHelper;
	private @Inject Plugin plugin;

	@Override
	public void open(Player player, Runnable confirm, Runnable reject, String upgrade) {
		Bukkit.getScheduler().runTask(plugin, () -> player.openInventory(GUIBuilder.builder(
				messageHandler.get(player, "upgrade.confirm.title"), 3
		)
				.addItem(genericHeadHelper.generateItem(
						ItemBuilder.newDyeItemBuilder(
								Material.STAINED_CLAY,
								1,
								DyeColor.GREEN
						)
								.setName(messageHandler.replacing(
										player, "upgrade.confirm.confirm",
										"%upgrade%", messageHandler.get(player, upgrade)
								))
								.build(),
						11,
						ClickType.LEFT,
						() -> {
							if (confirm != null) {
								confirm.run();
							}
						}
				))
				.addItem(genericHeadHelper.generateItem(
						ItemBuilder.newDyeItemBuilder(
								Material.STAINED_CLAY,
								1,
								DyeColor.RED
						)
								.setName(messageHandler.get(player, "upgrade.confirm.cancel"))
								.build(),
						15,
						ClickType.LEFT,
						() -> {
							if (reject != null) {
								reject.run();
							}
						}
				)).build()));
	}
}
