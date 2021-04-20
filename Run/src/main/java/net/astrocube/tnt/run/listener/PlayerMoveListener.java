package net.astrocube.tnt.run.listener;

import com.google.inject.Inject;
import net.astrocube.tnt.run.block.CoreBlockStepped;
import net.astrocube.tnt.run.block.TnTBlockHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @Inject
    private TnTBlockHandler tnTBlockHandler;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        Location location = player.getLocation();

        if (!player.hasMetadata("playing")) {
            return;
        }

        boolean value = player.getMetadata("playing").get(0).asBoolean();

        if (!value) {
            return;
        }

        Block blockDown = location.getBlock().getRelative(BlockFace.DOWN);

        if (blockDown.isLiquid() || blockDown.isEmpty() || blockDown.getType() == Material.AIR) {
            return;
        }

        if (tnTBlockHandler.containsBlock(blockDown)) {
            return;
        }

        tnTBlockHandler.addBlock(new CoreBlockStepped(blockDown.getLocation(), 1));

    }

}
