package net.astrocube.tnt.run.floor;

import com.google.inject.Singleton;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import static org.bukkit.util.NumberConversions.floor;

@Singleton
public class CoreFloorRemover implements FloorRemover {

	/**
	 * Distance used when checking blocks below a player,
	 * if it's small, it won't remove floor check when
	 * the distance is large, i.e. when a player jumps
	 */
	private static final float Y_DISTANCE_FACTOR = 0.8F;

	/**
	 * Distance used when checking blocks around and
	 * below the player, it must be large enough to
	 * make the player fall if they're over multiple
	 * blocks
	 */
	private static final float HORIZONTAL_DISTANCE_FACTOR = 0.3F;

	/**
	 * Directions where the algorithm will check, the
	 * arrays inside this matrix must have two as length,
	 * representing X, and Y {@link CoreFloorRemover#HORIZONTAL_DISTANCE_FACTOR}
	 * multipliers, it's all the possible combinations using
	 * 1 and -1 by default.
	 */
	private static final int[][] CHECK_DIRECTIONS = new int[][]{
			{1, 1}, {1, -1},
			{-1, 1}, {-1, -1}
	};

	@Override
	public void removeFloor(Location location) {

		double x = location.getX();
		double z = location.getZ();
		World world = location.getWorld();
		int y = floor(location.getY() - Y_DISTANCE_FACTOR);

		for (int[] direction : CHECK_DIRECTIONS) {
			Block block = world.getBlockAt(
					floor(x + HORIZONTAL_DISTANCE_FACTOR * direction[0]),
					y,
					floor(z + HORIZONTAL_DISTANCE_FACTOR * direction[1])
			);
			Block blockBelow = block.getRelative(BlockFace.DOWN);

			if ((blockBelow.getType() == Material.TNT)
					&& (block.getType() == Material.SAND || block.getType() == Material.GRAVEL)) {
				removeBlockMagically(block);
				removeBlockMagically(blockBelow);
			}
		}
	}

	private void removeBlockMagically(Block block) {
		block.setType(Material.AIR);
		block.getWorld().playEffect(block.getLocation().add(.5, .5, .5), Effect.SMOKE, 1, 1);
	}

}
