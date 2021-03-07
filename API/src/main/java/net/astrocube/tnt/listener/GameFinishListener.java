package net.astrocube.tnt.listener;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.match.MatchFinishEvent;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.tnt.podium.MatchProgress;
import net.astrocube.tnt.podium.MatchProgressHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.logging.Level;

public class GameFinishListener implements Listener {

    private @Inject MatchProgressHandler matchProgressHandler;
    private @Inject MessageHandler messageHandler;
    private @Inject FindService<Match> findService;
    private @Inject FindService<User> userFindService;
    private @Inject DisplayMatcher displayMatcher;
    private @Inject Plugin plugin;

    @EventHandler
    public void onGameFinish(MatchFinishEvent event) {

        List<MatchProgress.Participant> podium = matchProgressHandler.getPodium(event.getMatch());

        findService.find(event.getMatch()).callback(matchCallback ->
                matchCallback.ifSuccessful(match -> {

                    MatchParticipantsProvider.getInvolved(match).forEach(participant -> {

                        messageHandler.sendReplacing(
                                participant,
                                "game.podium.listing",
                                "%%first%%", generatePodiumSlug(podium, participant, 0),
                                "%%second%%", generatePodiumSlug(podium, participant, 1),
                                "%%third%%", generatePodiumSlug(podium, participant, 2)
                        );

                    });

                    matchProgressHandler.clearMatch(event.getMatch());

                })
        );

    }

    private String generatePodiumSlug(List<MatchProgress.Participant> podium, Player player, int position) {

        if (podium.size() <= position) {
            return messageHandler.get(player, "game.podium.empty");
        }

        MatchProgress.Participant participant = podium.get(position);

        User user;
        try {
            user = userFindService.findSync(participant.getPlayerId());
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error while obtaining user podium", e);
            return player.getName();
        }

        return displayMatcher.getDisplay(player, user).getColor() + user.getDisplay();

    }

}
