package net.astrocube.tnt.shared.perk;

import net.astrocube.api.bukkit.perk.AbstractPerk;

import java.beans.ConstructorProperties;

public class TNTPerkManifest implements AbstractPerk {

	private int money;
	private String runJumpTier;
	private String spleefJumpTier;
	private String spleefTripleShot;

	@ConstructorProperties({"money", "runJumpTier", "spleefJumpTier", "spleefTripleShot"})
	public TNTPerkManifest(
			int money,
			String runJumpTier,
			String spleefJumpTier,
			String spleefTripleShot
	) {
		this.money = money;
		this.runJumpTier = runJumpTier;
		this.spleefJumpTier = spleefJumpTier;
		this.spleefTripleShot = spleefTripleShot;
	}

	/**
	 * @return actual money of the player
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @param money to be updated.
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * @return actual tier of user's TNT run double jump.
	 */
	public String getRunJumpTier() {
		return runJumpTier;
	}

	/**
	 * @param tier to be updated at user's TNT run double jump.
	 */
	public void setRunJumpTier(String tier) {
		this.runJumpTier = tier;
	}

	/**
	 * @return actual tier of user's BowSpleef double jump.
	 */
	public String getSpleefJumpTier() {
		return spleefJumpTier;
	}

	/**
	 * @param tier to be updated at user's Bow Spleef double jump.
	 */
	public void setSpleefJumpTier(String tier) {
		this.spleefJumpTier = tier;
	}

	/**
	 * @return actual tier of user's Bow Spleef triple shot.
	 */
	public String getSpleefTripleShot() {
		return spleefTripleShot;
	}

	/**
	 * @param tier to be updated at user's Triple Shot.
	 */
	public void setSpleefTripleShot(String tier) {
		this.spleefTripleShot = tier;
	}

}
