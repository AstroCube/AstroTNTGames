package net.astrocube.tnt.spleef.listener;

import com.google.inject.Inject;
import net.astrocube.tnt.game.ScoreboardProvider;
import net.astrocube.tnt.perk.CachedPerkHandler;
import net.astrocube.tnt.perk.PerkProvider;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import javax.inject.Named;

public class TripleShotListener implements Listener {

	private @Inject
	@Named("tripleShot")
	CachedPerkHandler cachedPerkHandler;
	private @Inject ScoreboardProvider scoreboardProvider;

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {

			Player player = event.getPlayer();

			if (player.getInventory().getItemInHand().getType() == Material.BOW) {

				if (cachedPerkHandler.hasRemainingUses(player)) {
					Vector leftArrow = rotateVector(player.getLocation().getDirection(), -79.8);
					Vector playerDirection = rotateVector(player.getLocation().getDirection(), -80.1);
					Vector rightArrow = rotateVector(player.getLocation().getDirection(), -80.4);

					player.launchProjectile(Arrow.class, playerDirection);
					player.launchProjectile(Arrow.class, rightArrow);
					player.launchProjectile(Arrow.class, leftArrow);

					cachedPerkHandler.usePerk(player);
					scoreboardProvider.setupBoard(player);
				}

			}

		}

	}

	public Vector rotateVector(Vector vector, double whatAngle) {
		double sin = Math.cos(whatAngle);
		double cos = Math.sin(whatAngle);
		double x = vector.getX() * cos + vector.getZ() * sin;
		double z = vector.getX() * -sin + vector.getZ() * cos;

		return vector.setX(x).setZ(z);
	}

}
