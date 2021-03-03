package net.astrocube.tnt.run.floor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.tnt.run.event.CooldownFinishEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class CoreFloorCooldownChecker implements FloorCooldownChecker {

    private @Inject Plugin plugin;

    private final Set<String> registry = new HashSet<>();

    @Override
    public void scheduleCooldown(String match) {

        if (registry.contains(match)) {
            throw new IllegalArgumentException("Can not re-register match");
        }

        int cooldown = plugin.getConfig().getInt("game.cooldown", 5);

        registry.add(match);

        Bukkit.getScheduler().runTaskLater(
                plugin,
                () -> {
                    registry.remove(match);
                    Bukkit.getPluginManager().callEvent(new CooldownFinishEvent(match));
                },
                20L * cooldown
        );

    }

    @Override
    public boolean hasCooldown(String match) {
        return registry.contains(match);
    }

}
