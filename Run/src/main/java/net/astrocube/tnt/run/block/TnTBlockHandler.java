package net.astrocube.tnt.run.block;

import org.bukkit.block.Block;

import java.util.Set;

public interface TnTBlockHandler {

    void addBlock(BlockStepped blockStepped);

    void deleteBlock(BlockStepped blockStepped);

    boolean containsBlock(Block block);

    Set<BlockStepped> getBlocksStepped();

}
