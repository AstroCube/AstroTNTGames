package net.astrocube.tnt.run.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.tnt.perk.PerkProvider;
import net.astrocube.tnt.shared.perk.TNTPerkManifest;
import net.astrocube.tnt.shared.perk.TNTPerkProvider;
import net.astrocube.tnt.shared.perk.configuration.PerkConfiguration;
import net.astrocube.tnt.shared.perk.configuration.PerkConfigurationCache;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

@Singleton
public class CoreDoubleJumpProvider implements PerkProvider {

	private @Inject TNTPerkProvider tntPerkProvider;
	private @Inject PerkConfigurationCache perkConfigurationCache;
	private @Inject Plugin plugin;

	@Override
	public int getPlayerPerk(Player player) {

		try {
			TNTPerkManifest manifest = tntPerkProvider.getManifest(player.getDatabaseIdentifier())
					.orElseThrow(() -> new GameControlException("Manifest not found"));

			return perkConfigurationCache.getCachedItems()
					.stream()
					.filter(item -> item.getType().equals(PerkConfiguration.Purchasable.Type.RUN_JUMP) &&
							item.getName().equalsIgnoreCase(manifest.getRunJumpTier())
					)
					.map(PerkConfiguration.Purchasable::getPrice)
					.findFirst()
					.orElse(3);

		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "There was an error parsing player perks", e);
		}

		return 3;
	}

}
