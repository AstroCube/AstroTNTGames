package net.astrocube.tnt.lobby.statistic;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import net.astrocube.api.bukkit.board.Board;
import net.astrocube.api.bukkit.board.BoardProvider;
import net.astrocube.tnt.shared.money.MoneyTransactionHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Singleton
public class CoreLobbyScoreboardProvider implements LobbyScoreboardProvider {

	private @Inject MessageHandler messageHandler;
	private @Inject MoneyTransactionHandler moneyTransactionHandler;
	private @Inject ModeVictoryProvider modeVictoryProvider;
	private @Inject BoardProvider boardProvider;
	private @Inject Plugin plugin;

	@Override
	public void setup(Player player) {

		Board board = boardProvider.get(player)
				.orElseGet(() -> boardProvider.create(
						player,
						messageHandler.get(player, "board.title")
				));


		StringList scoreTranslation = messageHandler.replacingMany(

				player, "board.lines",
				"%money%", moneyTransactionHandler.getFormattedMoney(player.getDatabaseIdentifier()),

				"%run_victories%",
				modeVictoryProvider.getWonMatches(
						plugin.getConfig().getString("registry.tnt-run"),
						player.getDatabaseIdentifier()
				) + "",

				"%spleef_victories%",
				modeVictoryProvider.getWonMatches(
						plugin.getConfig().getString("registry.bow-spleef"),
						player.getDatabaseIdentifier()
				) + ""

		);

		board.setLines(scoreTranslation);
	}

}
