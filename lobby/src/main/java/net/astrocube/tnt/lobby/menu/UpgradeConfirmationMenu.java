package net.astrocube.tnt.lobby.menu;

import org.bukkit.entity.Player;

public interface UpgradeConfirmationMenu {

	/**
	 * Open confirmation menu for player
	 * @param player  to confirm
	 * @param confirm action
	 * @param reject  action
	 * @param upgrade text to add at confirmation translation
	 */
	void open(
			Player player,
			Runnable confirm,
			Runnable reject,
			String upgrade
	);

}
