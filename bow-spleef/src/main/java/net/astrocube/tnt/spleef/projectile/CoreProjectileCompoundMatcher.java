package net.astrocube.tnt.spleef.projectile;

import com.google.inject.Singleton;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.function.Consumer;

@Singleton
public class CoreProjectileCompoundMatcher implements ProjectileCompoundMatcher {
	@Override
	public ProjectileCompound getChosenCompound(Player player) {
		return new ProjectileCompound() {
			@Override
			public String getName() {
				return "default";
			}

			@Override
			public Consumer<ProjectileLaunchEvent> getRunnable() {
				return event -> {
					World world = player.getWorld();
					world.playEffect(event.getEntity().getLocation(), Effect.LAVA_POP, 1);
				};
			}
		};
	}
}
