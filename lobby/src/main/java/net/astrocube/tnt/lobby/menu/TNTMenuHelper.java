package net.astrocube.tnt.lobby.menu;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.core.gui.type.GUIBuilder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.function.Consumer;

public class TNTMenuHelper {

	private @Inject GenericHeadHelper genericHeadHelper;
	private @Inject MessageHandler messageHandler;
	private @Inject NumberFormat numberFormat;

	public void addDefaultButtons(GUIBuilder builder, Player player, int money, Consumer<Player> back) {
		builder.addItem(
				genericHeadHelper.generateDefaultClickable(
						genericHeadHelper.generateMetaAndPlace(
								new ItemStack(Material.DIAMOND),
								messageHandler.replacing(
										player, "store.percentage.title",
										"%money%",
										numberFormat.format(money)
								),
								messageHandler.getMany(player, "store.percentage.lore")
						),
						49,
						ClickType.LEFT,
						(p) -> {
						}
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
						back
				)
		);
	}

}
