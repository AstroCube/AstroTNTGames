package net.astrocube.tnt.run.block;

import org.bukkit.Location;

public interface BlockStepped {

    Location getLocation();

    int getSecondsLeft();

    void discountSecond();

}
