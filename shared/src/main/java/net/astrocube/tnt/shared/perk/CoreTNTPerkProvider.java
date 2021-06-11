package net.astrocube.tnt.shared.perk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.perk.PerkManifestProvider;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.perk.StorablePerk;
import net.astrocube.api.core.virtual.perk.StorablePerkDoc;

import java.util.Optional;
import java.util.Set;

@Singleton
public class CoreTNTPerkProvider implements TNTPerkProvider {

	private @Inject PerkManifestProvider perkManifestProvider;
	private @Inject UpdateService<StorablePerk, StorablePerkDoc.Partial> updateService;
	private @Inject ObjectMapper mapper;

	@Override
	public Optional<TNTPerkManifest> getManifest(String playerId) throws Exception {
		return getFromUser(playerId).stream().map(manifest -> (TNTPerkManifest) manifest.getStored()).findAny();
	}

	@Override
	public void update(String playerId, TNTPerkManifest manifest) throws Exception {

		StorablePerk perk = getFromUser(playerId).stream().findAny().orElseThrow(
				() -> new GameControlException("Not found any storable perk to be updated.")
		);

		perk.setStored(manifest);
		updateService.updateSync(perk);

	}

	private Set<StorablePerk> getFromUser(String playerId) throws Exception {
		ObjectNode node = mapper.createObjectNode();

		node.put("responsible", playerId);
		node.put("type", "tnt_games_manifest");

		return perkManifestProvider.query(node, TNTPerkManifest.class);
	}

}
