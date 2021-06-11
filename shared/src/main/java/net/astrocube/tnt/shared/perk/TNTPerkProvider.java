package net.astrocube.tnt.shared.perk;

import net.astrocube.api.core.virtual.perk.StorablePerk;

import java.util.Optional;

public interface TNTPerkProvider {

	static TNTPerkManifest generateDefault() {
		return new TNTPerkManifest() {
			@Override
			public int getMoney() {
				return 0;
			}

			@Override
			public void setMoney(int money) {
			}

			@Override
			public String getRunJumpTier() {
				return "rookie";
			}

			@Override
			public void setRunJumpTier(String tier) {
			}

			@Override
			public String getSpleefJumpTier() {
				return "rookie";
			}

			@Override
			public void setSpleefJumpTier(String tier) {
			}

			@Override
			public String getSpleefTripleShot() {
				return "rookie";
			}

			@Override
			public void setSpleefTripleShot(String tier) {
			}
		};
	}

	/**
	 * Retrieves perk manifest from {@link StorablePerk}.
	 * @param playerId to retrieve
	 * @return optional containing possible manifest
	 */
	Optional<TNTPerkManifest> getManifest(String playerId) throws Exception;

	/**
	 * updates a certain manifest according to player stored {@link StorablePerk}.
	 * @param playerId to update
	 * @param manifest to update
	 */
	void update(String playerId, TNTPerkManifest manifest) throws Exception;

}
