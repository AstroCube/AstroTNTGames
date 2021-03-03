package net.astrocube.tnt.run.floor;

public interface FloorCooldownChecker {

    /**
     * Schedules a cooldown preventing floor removal.
     * @param match to register.
     */
    void scheduleCooldown(String match);

    /**
     * Check if match of user has cooldown.
     * @param match to check.
     * @return if has cooldown.
     */
    boolean hasCooldown(String match);

}
