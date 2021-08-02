package net.astrocube.tnt.spleef.listener;

import com.google.inject.Inject;
import net.astrocube.tnt.game.ScoreboardProvider;
import net.astrocube.tnt.perk.CachedPerkHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import javax.inject.Named;

public class MultiShotListener implements Listener {

	private static final double[][] ANGLES;

	private static double[] sinCos(double angle) {
		return new double[] {
			Math.sin(angle),
			Math.cos(angle)
		};
	}

	static {
		double delta = Math.toRadians(30);
		ANGLES = new double[][] {
			sinCos(-delta),
			sinCos(0),
			sinCos(delta)
		};
	}

	private @Inject @Named("tripleShot") CachedPerkHandler cachedPerkHandler;
	private @Inject ScoreboardProvider scoreboardProvider;

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {

			Player player = event.getPlayer();

			if (player.getInventory().getItemInHand().getType() == Material.BOW) {

				if (cachedPerkHandler.hasRemainingUses(player)) {
					Vector playerDirection = player.getLocation().getDirection();

					for (double[] angle : ANGLES) {
						double sin = angle[0];
						double cos = angle[1];
						double x = playerDirection.getX() * cos + playerDirection.getZ() * sin;
						double z = playerDirection.getX() * -sin + playerDirection.getZ() * cos;

						Vector direction = new Vector(x, playerDirection.getY(), z);
						player.launchProjectile(Arrow.class, direction);
					}

					cachedPerkHandler.usePerk(player);
					scoreboardProvider.setupBoard(player);
				}
			}
		}
	}

}
