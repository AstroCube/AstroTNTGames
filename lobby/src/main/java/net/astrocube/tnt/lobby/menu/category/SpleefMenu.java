package net.astrocube.tnt.lobby.menu.category;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.tnt.lobby.menu.MainShopMenu;
import net.astrocube.tnt.lobby.menu.TNTMenuHelper;
import net.astrocube.tnt.lobby.menu.UpgradeShopMenu;
import net.astrocube.tnt.shared.perk.configuration.PerkConfiguration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

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
				() -> mainShopMenu.open(player)
		);


		ItemStack jumpIcon = ItemBuilder.newBuilder(Material.DIAMOND_BOOTS)
				.setName(messageHandler.get(player, "child.spleef.double-jump.title"))
				.setLore(messageHandler.getMany(player, "child.spleef.double-jump.lore"))
				.build();

		ItemStack tripleShotIcon = ItemBuilder.newBuilder(Material.ARROW)
				.setName(messageHandler.get(player, "child.spleef.triple-shot.title"))
				.setLore(messageHandler.getMany(player, "child.spleef.triple-shot.lore"))
				.build();

		builder.addItem(genericHeadHelper.generateItem(
				jumpIcon, 20, ClickType.LEFT,
				() -> upgradeShopMenu.open(
						player, money,
						PerkConfiguration.Purchasable.Type.SPLEEF_JUMP,
						jumpIcon,
						() -> open(player, money)
				)
		))
				.addItem(genericHeadHelper.generateItem(
						tripleShotIcon, 24, ClickType.LEFT,
						() -> upgradeShopMenu.open(
								player, money,
								PerkConfiguration.Purchasable.Type.SPLEEF_SHOT,
								tripleShotIcon,
								() -> open(player, money)
						)
				));

		player.openInventory(builder.build());

	}

}
