package net.astrocube.tnt.spleef.listener;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

public class EntityExplodeListener implements Listener {

	@EventHandler
	public void onExplode(EntityExplodeEvent event) {

		if (event.getEntityType() != EntityType.PRIMED_TNT) {
			return;
		}
		event.setCancelled(true);
		event.getEntity().remove();

	}

	@EventHandler
	public void onIgnite(BlockIgniteEvent event) {
		if (event.getIgnitingBlock().getType() == Material.TNT) {
			event.getIgnitingEntity().setVelocity(new Vector().zero());
		}
	}

}
