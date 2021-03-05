package net.astrocube.tnt.run.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import net.astrocube.api.bukkit.board.ScoreboardManagerProvider;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.unnamed.uboard.ScoreboardObjective;
import team.unnamed.uboard.builder.ScoreboardBuilder;

import java.util.Collections;
import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreScoreboardProvider implements ScoreboardProvider {

    private @Inject ScoreboardManagerProvider scoreboardManagerProvider;
    private @Inject MessageHandler messageHandler;
    private @Inject ActualMatchCache actualMatchCache;
    private @Inject Plugin plugin;

    @Override
    public void setupBoard(Player player) {

        Optional<ScoreboardObjective> objectiveOptional =
                scoreboardManagerProvider.getScoreboard().getScoreboard("tntrun_" + player.getDatabaseIdentifier());
        int alive;

        try {
            Optional<Match> matchOptional = actualMatchCache.get(player.getDatabaseIdentifier());

            if (matchOptional.isPresent()) {

                Match match = matchOptional.get();

                if (UserMatchJoiner.checkOrigin(player.getDatabaseIdentifier(), match) != UserMatchJoiner.Origin.PLAYING) {
                    return;
                }

                alive = (int) match.getTeams().parallelStream()
                        .map(MatchDoc.Team::getMembers)
                        .flatMap(u -> u.stream().filter(MatchDoc.TeamMember::isActive))
                        .count();

            } else {
                return;
            }

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error obtaining actual match", e);
            return;
        }

        StringList scoreTranslation = messageHandler.replacingMany(
                player, "game.board.lines",
                "%%survivors%%", alive,
                "%%jumps%%", 1
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
