package net.astrocube.tnt.lobby.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.lobby.event.LobbyJoinEvent;
import net.astrocube.api.bukkit.perk.PerkManifestProvider;
import net.astrocube.tnt.lobby.menu.MainShopMenu;
import net.astrocube.tnt.lobby.statistic.LobbyScoreboardProvider;
import net.astrocube.tnt.shared.perk.TNTPerkProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class GameLobbyJoinListener implements Listener {

	private @Inject LobbyScoreboardProvider lobbyScoreboardProvider;
	private @Inject TNTPerkProvider perkProvider;
	private @Inject PerkManifestProvider perkManifestProvider;
	private @Inject MainShopMenu mainShopMenu;
	private @Inject Plugin plugin;

	@EventHandler
	public void onLobbyJoin(LobbyJoinEvent event) {

		Bukkit.getScheduler().runTask(plugin, () -> {
			lobbyScoreboardProvider.setup(event.getPlayer());

			try {
				perkProvider.getManifest(event.getPlayer().getDatabaseIdentifier())
						.orElseThrow(() -> new GameControlException("Manifest not found"));
				event.getPlayer().getInventory().setItem(4, mainShopMenu.generateItem(event.getPlayer()));
			} catch (GameControlException e) {
				try {
					perkManifestProvider.createRegistry(
							event.getPlayer().getDatabaseIdentifier(),
							plugin.getConfig().getString("registry.mode"),
							null,
							"tnt_games_manifest",
							TNTPerkProvider.generateDefault()
					);
				} catch (Exception exception) {
					kickOnError(event.getPlayer(), e);
				}
			} catch (Exception e) {
				kickOnError(event.getPlayer(), e);
			}

		});
	}

	private void kickOnError(Player player, Exception e) {
		plugin.getLogger().log(Level.SEVERE, "There was an error retrieving user perk manifest", e);
		player.kickPlayer(ChatColor.RED + "Error while obtaining TNT Perk manifest.");
	}

}
