package net.astrocube.tnt.lobby.listener;

import com.google.api.client.http.HttpResponseException;
import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.lobby.event.LobbyJoinEvent;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.http.exception.NotFound;
import net.astrocube.tnt.lobby.statistic.LobbyScoreboardProvider;
import net.astrocube.tnt.shared.perk.PerkManifestProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class GameLobbyJoinListener implements Listener {

    private @Inject LobbyScoreboardProvider lobbyScoreboardProvider;
    private @Inject PerkManifestProvider perkManifestProvider;
    private @Inject Plugin plugin;

    @EventHandler
    public void onLobbyJoin(LobbyJoinEvent event) {

        Bukkit.getScheduler().runTask(plugin, () -> {
            lobbyScoreboardProvider.setup(event.getPlayer());

            try {
                perkManifestProvider.getManifest(event.getPlayer().getDatabaseIdentifier())
                        .orElseThrow(() -> new GameControlException("Manifest not found"));
            } catch (GameControlException e) {
                try {
                    perkManifestProvider.createRegistry(event.getPlayer().getDatabaseIdentifier(), plugin.getConfig().getString("registry.mode"));
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
