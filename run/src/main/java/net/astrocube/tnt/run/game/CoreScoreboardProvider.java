package net.astrocube.tnt.run.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import net.astrocube.api.bukkit.board.ScoreboardManagerProvider;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.tnt.perk.CachedPerkHandler;
import net.astrocube.tnt.game.ScoreboardProvider;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.unnamed.uboard.ScoreboardObjective;
import team.unnamed.uboard.builder.ScoreboardBuilder;

import javax.inject.Named;
import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreScoreboardProvider implements ScoreboardProvider {

	private @Inject ScoreboardManagerProvider scoreboardManagerProvider;
	private @Inject
	@Named("doubleJump")
	CachedPerkHandler cachedPerkHandler;
	private @Inject MessageHandler messageHandler;
	private @Inject ActualMatchCache actualMatchCache;
	private @Inject Plugin plugin;

	@Override
	public void setupBoard(Player player) {

		Optional<ScoreboardObjective> objectiveOptional =
				scoreboardManagerProvider.getScoreboard().getScoreboard("tntrun_" + player.getDatabaseIdentifier());
		int alive;
		boolean playing = false;

		try {
			Optional<Match> matchOptional = actualMatchCache.get(player.getDatabaseIdentifier());

			if (matchOptional.isPresent()) {

				Match match = matchOptional.get();

				alive = (int) match.getTeams().parallelStream()
						.map(MatchDoc.Team::getMembers)
						.flatMap(u -> u.stream().filter(MatchDoc.TeamMember::isActive))
						.count();

				if (MatchParticipantsProvider.getOnlinePlayers(match, t -> true).contains(player)) {
					playing = true;
				}

			} else {
				return;
			}

		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Error obtaining actual match", e);
			return;
		}

		StringList scoreTranslation = messageHandler.replacingMany(
				player, "game.board.lines",
				"%survivors%", alive,
				"%jumps%", playing ? cachedPerkHandler.getRemainingUses(player) : messageHandler.get(player, "game.board.empty")
		);

		if (!objectiveOptional.isPresent()) {
			ScoreboardBuilder builder =
					scoreboardManagerProvider.getScoreboard().newScoreboard("tntrun_" + player.getDatabaseIdentifier());
			scoreTranslation.forEach(builder::addLine);
			builder.setTitle(messageHandler.get(player, "game.board.title"));
			scoreboardManagerProvider.getScoreboard().setToPlayer(player, builder.build());
		} else {
			objectiveOptional.get().setStringLines(scoreTranslation);
			objectiveOptional.get().updateScoreboard();
		}

	}

}
