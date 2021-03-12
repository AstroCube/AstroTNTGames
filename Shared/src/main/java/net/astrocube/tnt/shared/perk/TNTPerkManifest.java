package net.astrocube.tnt.shared.perk;

public interface TNTPerkManifest {

    /**
     * @return actual money of the player
     */
    int getMoney();

    /**
     * @param money to be updated.
     */
    void setMoney(int money);

    /**
     * @return actual tier of user's TNT run double jump.
     */
    DoubleJumpTier getRunJumpTier();

    /**
     * @param tier to be updated at user's TNT run double jump.
     */
    void setRunJumpTier(DoubleJumpTier tier);

    /**
     * @return actual tier of user's BowSpleef double jump.
     */
    DoubleJumpTier getSpleefJumpTier();

    /**
     * @param tier to be updated at user's Bow Spleef double jump.
     */
    void setSpleefJumpTier(DoubleJumpTier tier);

    /**
     * @return actual tier of user's Bow Spleef triple shot.
     */
    TripleShotTier getSpleefTripleShot();

    /**
     * @param tier to be updated at user's Triple Shot.
     */
    void setSpleefTripleShot(TripleShotTier tier);

}
