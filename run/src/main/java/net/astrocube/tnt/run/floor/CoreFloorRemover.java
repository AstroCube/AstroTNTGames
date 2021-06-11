package net.astrocube.tnt.run.floor;

import com.google.inject.Singleton;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class CoreFloorRemover implements FloorRemover {

	@Override
	public void removeFloor(Location location) {
		for (Block block : getBlocksBelow(location)) {
			Block blockBelow = block.getRelative(BlockFace.DOWN);
			if ((blockBelow.getType() == Material.TNT) && (block.getType() == Material.SAND || block.getType() == Material.GRAVEL)) {
				block.setType(Material.AIR);
				blockBelow.setType(Material.AIR);

				block.getWorld().playEffect(block.getLocation().add(.5, .5, .5), Effect.SMOKE, 1, 1);
				blockBelow.getWorld().playEffect(block.getLocation().add(.5, .5, .5), Effect.SMOKE, 1, 1);
			}
		}
	}

	private List<Block> getBlocksBelow(Location entityLocation) {

		List<Block> blocksBelow = new ArrayList<>();
		double x = entityLocation.getX();
		double z = entityLocation.getZ();
		World world = entityLocation.getWorld();
		double yBelow = entityLocation.getY() - 0.0001;

		Block northEast = new Location(world, x + 0.3, yBelow, z - 0.3).getBlock();
		Block northWest = new Location(world, x - 0.3, yBelow, z - 0.3).getBlock();
		Block southEast = new Location(world, x + 0.3, yBelow, z + 0.3).getBlock();
		Block southWest = new Location(world, x - 0.3, yBelow, z + 0.3).getBlock();
		Block[] blocks = {northEast, northWest, southEast, southWest};

		for (Block block : blocks) {

			if (!blocksBelow.isEmpty()) {

				boolean duplicateExists = false;

				for (Block value : blocksBelow) {
					if (value.equals(block)) {
						duplicateExists = true;
						break;
					}
				}

				if (!duplicateExists) {
					blocksBelow.add(block);
				}

			} else {
				blocksBelow.add(block);
			}
		}

		return blocksBelow;

	}

}
