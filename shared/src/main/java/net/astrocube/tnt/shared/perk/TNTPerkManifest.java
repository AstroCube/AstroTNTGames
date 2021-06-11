package net.astrocube.tnt.shared.perk;

import net.astrocube.api.bukkit.perk.AbstractPerk;

public interface TNTPerkManifest extends AbstractPerk {

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
	String getRunJumpTier();

	/**
	 * @param tier to be updated at user's TNT run double jump.
	 */
	void setRunJumpTier(String tier);

	/**
	 * @return actual tier of user's BowSpleef double jump.
	 */
	String getSpleefJumpTier();

	/**
	 * @param tier to be updated at user's Bow Spleef double jump.
	 */
	void setSpleefJumpTier(String tier);

	/**
	 * @return actual tier of user's Bow Spleef triple shot.
	 */
	String getSpleefTripleShot();

	/**
	 * @param tier to be updated at user's Triple Shot.
	 */
	void setSpleefTripleShot(String tier);

}
