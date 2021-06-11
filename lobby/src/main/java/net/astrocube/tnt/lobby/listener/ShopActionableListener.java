package net.astrocube.tnt.lobby.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.user.inventory.event.ActionableItemEvent;
import net.astrocube.tnt.lobby.menu.MainShopMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class ShopActionableListener implements Listener {

	private @Inject MainShopMenu mainShopMenu;

	@EventHandler
	public void onShopAction(ActionableItemEvent event) {
		if (event.getAction().equalsIgnoreCase("tnt_shop") &&
				(event.getClick() == Action.RIGHT_CLICK_AIR || event.getClick() == Action.RIGHT_CLICK_BLOCK)) {
			mainShopMenu.open(event.getPlayer());
		}
	}

}
