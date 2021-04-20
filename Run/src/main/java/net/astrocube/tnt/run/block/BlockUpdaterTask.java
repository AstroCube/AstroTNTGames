package net.astrocube.tnt.run.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockUpdaterTask implements Runnable {

    private final TnTBlockHandler tnTBlockHandler;

    public BlockUpdaterTask(TnTBlockHandler tnTBlockHandler) {
        this.tnTBlockHandler = tnTBlockHandler;
    }

    @Override
    public void run() {
        for (BlockStepped blockStepped : tnTBlockHandler.getBlocksStepped()) {
                setAirBlock(blockStepped);
                tnTBlockHandler.deleteBlock(blockStepped);
        }
    }

    private void setAirBlock(BlockStepped blockStepped) {
        Block block = blockStepped.getLocation().getBlock();
        block.setType(Material.AIR);
        block.getRelative(BlockFace.DOWN).setType(Material.AIR);
    }

}
