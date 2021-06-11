package net.astrocube.tnt.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.game.GameUserDisconnectEvent;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.tnt.event.PlayerDisqualificationEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

public class GameUserDisconnectListener implements Listener {

	private @Inject ActualMatchCache actualMatchCache;
	private @Inject Plugin plugin;

	@EventHandler
	public void onGameUserDisconnect(GameUserDisconnectEvent event) {

		try {

			Optional<Match> matchOptional = actualMatchCache.get(event.getPlayer().getDatabaseIdentifier());

			matchOptional.ifPresent(match -> {

				try {
					if (UserMatchJoiner.checkOrigin(event.getPlayer().getDatabaseIdentifier(), match) == UserMatchJoiner.Origin.PLAYING) {
						Bukkit.getPluginManager().callEvent(new PlayerDisqualificationEvent(event.getPlayer(), match.getId()));
					}
				} catch (GameControlException ignore) {
				}

			});

		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "There was an error trying to obtain match from user disconnection", e);
			Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(event.getMatch(), false));
		}

	}

}
