package net.astrocube.tnt.spleef.listener;

import com.google.inject.Inject;
import net.astrocube.tnt.spleef.projectile.ProjectileCompoundMatcher;
import net.astrocube.tnt.spleef.projectile.ProjectileTaskTracker;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class ProjectileLaunchListener implements Listener {

	private @Inject Plugin plugin;
	private @Inject ProjectileCompoundMatcher projectileCompoundMatcher;
	private @Inject ProjectileTaskTracker projectileTaskTracker;

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {

		if (event.getEntity().getShooter() instanceof Player) {

			Player player = (Player) event.getEntity().getShooter();

			player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1f, 1f);

			event.getEntity().setFireTicks(10);

			projectileTaskTracker.schedule(
					event.getEntity().getEntityId(),
					new BukkitRunnable() {
						@Override
						public void run() {
							projectileCompoundMatcher.getChosenCompound(player).getRunnable().accept(event);
						}
					}.runTaskTimer(plugin, (long) (0.5 * 1L), 1L));
		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {

		projectileTaskTracker.unlink(event.getEntity().getEntityId());
		event.getEntity().remove();

	}

}
