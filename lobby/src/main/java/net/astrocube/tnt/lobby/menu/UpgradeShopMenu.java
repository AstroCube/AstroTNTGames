package net.astrocube.tnt.lobby.menu;

import net.astrocube.tnt.shared.perk.configuration.PerkConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface UpgradeShopMenu {

	/**
	 * Open a player upgrade shop menu
	 * @param player to display menu
	 * @param money  to display at GUI
	 * @param type   of purchasable
	 * @param icon   of parent category
	 * @param goBack consumer
	 */
	void open(
			Player player,
			int money,
			PerkConfiguration.Purchasable.Type type,
			ItemStack icon,
			Runnable goBack
	);

}
