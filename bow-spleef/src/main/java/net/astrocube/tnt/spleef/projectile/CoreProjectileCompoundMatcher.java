package net.astrocube.tnt.spleef.projectile;

import com.google.inject.Singleton;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Singleton
public class CoreProjectileCompoundMatcher implements ProjectileCompoundMatcher {

	@Override
	public ProjectileCompound getChosenCompound(Player player) {
		return new ProjectileCompound(
				"default",
				event -> {
					World world = player.getWorld();
					world.playEffect(event.getEntity().getLocation(), Effect.LAVA_POP, 1);
				}
		);
	}

}
