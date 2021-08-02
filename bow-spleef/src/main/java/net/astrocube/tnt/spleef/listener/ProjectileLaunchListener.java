package net.astrocube.tnt.spleef.listener;

import com.google.inject.Inject;
import net.astrocube.tnt.spleef.projectile.ProjectileCompoundMatcher;
import net.astrocube.tnt.spleef.projectile.ProjectileTaskTracker;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ProjectileLaunchListener implements Listener {

	/**
	 * Set of entity uuids that hit a player,
	 * so they don't get removed after that
	 */
	private final Set<UUID> hits = new HashSet<>();

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
					}.runTaskTimer(plugin, 0L, 2L));
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		System.out.println("a");
		if (event.getEntity() instanceof Player) {
			System.out.println("b");
			Entity damager = event.getDamager();
			if (damager instanceof Arrow) {
				System.out.println("c");
				Arrow arrow = (Arrow) damager;
				arrow.setVelocity(arrow.getVelocity().multiply(new Vector(-1, 1, -1)));
				hits.add(arrow.getUniqueId());
				event.setCancelled(true);
 			}
		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		Projectile projectile = event.getEntity();
		Bukkit.getScheduler().runTaskLater(
			plugin,
			() -> {
				if (hits.remove(projectile.getUniqueId())) {
					return;
				}
				projectileTaskTracker.unlink(projectile.getEntityId());
				projectile.remove();
			},
			2L
		);
	}

}
