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
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.logging.Level;

@Singleton
public class CoreMainShopMenu implements MainShopMenu {

	private @Inject MessageHandler messageHandler;
	private @Inject GenericHeadHelper genericHeadHelper;
	private @Inject TNTPerkProvider tntPerkProvider;
	private @Inject TNTMenuHelper tntMenuHelper;
	private @Inject Plugin plugin;

	private @Inject
	@Named("spleef")
	MainShopMenu.SubMenu spleefMenu;
	private @Inject
	@Named("run")
	MainShopMenu.SubMenu runMenu;

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

		int finalMoney = money;
		GUIBuilder builder = GUIBuilder.builder(messageHandler.get(player, "store.title"))
				.addItem(genericHeadHelper.generateItem(
						ItemBuilder.newBuilder(Material.TNT)
								.setName(messageHandler.get(player, "store.categories.run.title"))
								.setLore(messageHandler.getMany(player, "store.categories.run.lore"))
								.build(),
						20,
						ClickType.LEFT,
						() -> runMenu.open(player, finalMoney)
				))
				.addItem(genericHeadHelper.generateItem(
						ItemBuilder.newBuilder(Material.BOW)
								.setName(messageHandler.get(player, "store.categories.spleef.title"))
								.setLore(messageHandler.getMany(player, "store.categories.spleef.lore"))
								.build(),
						24,
						ClickType.LEFT,
						() -> spleefMenu.open(player, finalMoney)
				));


		tntMenuHelper.addDefaultButtons(
				builder,
				player,
				money,
				player::closeInventory
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
