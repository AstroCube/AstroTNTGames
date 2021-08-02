package net.astrocube.tnt.spleef.projectile;

import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class CoreProjectileTaskTracker implements ProjectileTaskTracker {

	private final Map<Integer, Integer> runnables = new HashMap<>();

	@Override
	public void schedule(int arrow, BukkitTask bukkitRunnable) {
		runnables.put(arrow, bukkitRunnable.getTaskId());
	}

	@Override
	public void unlink(int arrow) {
		Integer taskId = runnables.get(arrow);
		if (taskId != null) {
			Bukkit.getScheduler().cancelTask(taskId);
			runnables.remove(arrow);
		}
	}
}
