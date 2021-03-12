package net.astrocube.tnt.lobby.statistic;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import net.astrocube.api.bukkit.board.ScoreboardManagerProvider;
import net.astrocube.tnt.shared.money.MoneyTransactionHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.unnamed.uboard.ScoreboardObjective;
import team.unnamed.uboard.builder.ScoreboardBuilder;

import java.util.Optional;

@Singleton
public class CoreLobbyScoreboardProvider implements LobbyScoreboardProvider {

    private @Inject MessageHandler messageHandler;
    private @Inject ScoreboardManagerProvider scoreboardManagerProvider;
    private @Inject MoneyTransactionHandler moneyTransactionHandler;
    private @Inject ModeVictoryProvider modeVictoryProvider;
    private @Inject Plugin plugin;

    @Override
    public void setup(Player player) {

        Optional<ScoreboardObjective> objectiveOptional =
                scoreboardManagerProvider.getScoreboard().getScoreboard("tntlobby_" + player.getDatabaseIdentifier());

        StringList scoreTranslation = messageHandler.replacingMany(

                player, "board.lines",
                "%%money%%", moneyTransactionHandler.getFormattedMoney(player.getDatabaseIdentifier()),

                "%%run_victories%%",
                modeVictoryProvider.getWonMatches(
                        plugin.getConfig().getString("registry.tnt-run"),
                        player.getDatabaseIdentifier()
                ),

                "%%spleef_victories%%",
                modeVictoryProvider.getWonMatches(
                        plugin.getConfig().getString("registry.bow-spleef"),
                        player.getDatabaseIdentifier()
                )

        );

        if (!objectiveOptional.isPresent()) {
            ScoreboardBuilder builder =
                    scoreboardManagerProvider.getScoreboard().newScoreboard("tntlobby_" + player.getDatabaseIdentifier());
            scoreTranslation.forEach(builder::addLine);
            builder.setTitle(messageHandler.get(player, "board.title"));
            scoreboardManagerProvider.getScoreboard().setToPlayer(player, builder.build());
        } else {
            objectiveOptional.get().setStringLines(scoreTranslation);
            objectiveOptional.get().updateScoreboard();
        }

    }

}
