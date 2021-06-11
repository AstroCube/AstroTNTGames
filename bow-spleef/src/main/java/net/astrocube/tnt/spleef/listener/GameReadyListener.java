package net.astrocube.tnt.spleef.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.game.GameReadyEvent;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.event.match.MatchStartEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.MapConfigurationProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.tnt.game.PlayerSpawner;
import net.astrocube.tnt.game.ScoreboardProvider;
import net.astrocube.tnt.map.MapConfiguration;
import net.astrocube.tnt.perk.CachedPerkHandler;
import net.astrocube.tnt.podium.MatchProgressHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.inject.Named;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class GameReadyListener implements Listener {

	private @Inject FindService<Match> findService;

	private @Inject MapConfigurationProvider mapConfigurationProvider;
	private @Inject Plugin plugin;

	private @Inject PlayerSpawner playerSpawner;
	private @Inject ScoreboardProvider scoreboardProvider;
	private @Inject MatchProgressHandler matchProgressHandler;
	private @Inject
	@Named("doubleJump")
	CachedPerkHandler cachedPerkHandler;
	private @Inject
	@Named("tripleShot")
	CachedPerkHandler tripleShotHandler;

	@EventHandler
	public void onGameReady(GameReadyEvent event) {

		findService.find(event.getMatch()).callback(response -> {

			try {

				if (!response.isSuccessful()) {
					throw new GameControlException("The requested match was not found");
				}

				MapConfiguration configuration =
						mapConfigurationProvider.parseConfiguration(event.getConfiguration(), MapConfiguration.class);
				matchProgressHandler.registerMatch(event.getMatch(), event.getTeams().stream().flatMap(team ->
						team.getMembers().stream().map(MatchDoc.TeamMember::getUser)
				).collect(Collectors.toSet()));

				int cooldown = plugin.getConfig().getInt("game.cooldown", 5);

				response.ifSuccessful(match -> {

					executeForInvolved(
							event.getTeams(),
							(p) -> {
								playerSpawner.spawn(p, match.getId(), configuration.getSpawn());
								cachedPerkHandler.registerJumps(p);
								tripleShotHandler.registerJumps(p);
								playerSpawner.announce(p);

								ItemStack bow = new ItemStack(Material.BOW);
								bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
								bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);

								p.getInventory().clear();
								p.getInventory().setItem(0, bow);
								p.getInventory().setItem(9, new ItemStack(Material.ARROW));

							});

					Bukkit.getScheduler().runTaskLater(
							plugin,
							() -> executeForInvolved(event.getTeams(), (p) -> {
								p.setAllowFlight(true);
								scoreboardProvider.setupBoard(p);
							}),
							20L * cooldown
					);

					Bukkit.getPluginManager().callEvent(new MatchStartEvent(match.getId()));

				});

			} catch (Exception e) {
				plugin.getLogger().log(Level.SEVERE, "There was an error processing game ready event.", e);
				Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(event.getMatch(), false));
			}

		});

	}

	private void executeForInvolved(Set<MatchDoc.Team> teamSet, Consumer<Player> consumer) {
		teamSet.stream()
				.flatMap(team ->
						team.getMembers().stream().map(MatchDoc.TeamMember::getUser)
				)
				.forEach(player -> {

					Player online = Bukkit.getPlayerByIdentifier(player);

					if (online != null) {

						consumer.accept(online);

					}

				});
	}

}
