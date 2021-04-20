package net.astrocube.tnt.run.block;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CoreTnTBlockHandler implements TnTBlockHandler {

    private final Map<Location, BlockStepped> blocks = new ConcurrentHashMap<>();

    @Override
    public void addBlock(BlockStepped blockStepped) {
        blocks.put(blockStepped.getLocation(), blockStepped);
    }

    @Override
    public void deleteBlock(BlockStepped blockStepped) {
        blocks.remove(blockStepped.getLocation());
    }

    @Override
    public boolean containsBlock(Block block) {
        return blocks.containsKey(block.getLocation());
    }

    @Override
    public Set<BlockStepped> getBlocksStepped() {
        return new HashSet<>(blocks.values());
    }
}
