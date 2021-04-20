package net.astrocube.tnt.run.block;

import org.bukkit.Location;

public class CoreBlockStepped implements BlockStepped {

    private final Location location;
    private int secondsLeft;

    public CoreBlockStepped(Location location, int secondsLeft) {
        this.location = location;
        this.secondsLeft = secondsLeft;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getSecondsLeft() {
        return secondsLeft;
    }

    @Override
    public void discountSecond() {
        secondsLeft--;
    }

}
