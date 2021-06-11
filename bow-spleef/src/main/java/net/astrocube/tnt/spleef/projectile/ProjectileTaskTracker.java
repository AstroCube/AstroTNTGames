package net.astrocube.tnt.spleef.projectile;

import org.bukkit.scheduler.BukkitTask;

public interface ProjectileTaskTracker {

	/**
	 * Track registry of task for posterior invalidation.
	 * @param arrow          to check
	 * @param bukkitRunnable to schedule
	 */
	void schedule(int arrow, BukkitTask bukkitRunnable);

	/**
	 * Unlink registry
	 * @param arrow to unlink
	 */
	void unlink(int arrow);

}
