package net.astrocube.tnt.spleef.listener;

import com.google.inject.Inject;
import net.astrocube.tnt.spleef.projectile.ProjectileCompoundMatcher;
import net.astrocube.tnt.spleef.projectile.ProjectileTaskTracker;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

public class ProjectileLaunchListener implements Listener {

	private @Inject Plugin plugin;
	private @Inject ProjectileCompoundMatcher projectileCompoundMatcher;
	private @Inject ProjectileTaskTracker projectileTaskTracker;

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {

		Projectile projectile = event.getEntity();
		ProjectileSource shooter = projectile.getShooter();

		if (shooter instanceof Player) {

			Player player = (Player) shooter;

			player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1f, 1f);

			projectile.setFireTicks(Integer.MAX_VALUE);

			projectileTaskTracker.schedule(
					projectile.getEntityId(),
					new BukkitRunnable() {
						@Override
						public void run() {
							projectileCompoundMatcher.getChosenCompound(player).getRunnable().accept(event);
						}
					}.runTaskTimer(plugin, 0L, 1L));
		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		Projectile projectile = event.getEntity();
		projectileTaskTracker.unlink(projectile.getEntityId());
		projectile.remove();
	}

}
