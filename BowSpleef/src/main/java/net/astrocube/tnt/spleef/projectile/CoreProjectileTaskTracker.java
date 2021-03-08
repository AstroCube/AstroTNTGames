package net.astrocube.tnt.spleef.projectile;

import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class CoreProjectileTaskTracker implements ProjectileTaskTracker {

    private Map<Integer, Integer> runnables = new HashMap<>();

    @Override
    public void schedule(int arrow, BukkitTask bukkitRunnable) {
        runnables.put(arrow, bukkitRunnable.getTaskId());
    }

    @Override
    public void unlink(int arrow) {
        if (runnables.containsKey(arrow)) {
            int cancellable = runnables.get(arrow);
            Bukkit.getScheduler().cancelTask(cancellable);
            runnables.remove(arrow);
        }
    }
}
