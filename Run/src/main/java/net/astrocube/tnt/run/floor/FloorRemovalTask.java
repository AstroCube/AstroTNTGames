package net.astrocube.tnt.run.floor;

import lombok.AllArgsConstructor;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

@AllArgsConstructor
public class FloorRemovalTask implements Runnable {

    private static final long DELAY_MS = 160;

    private final ActualMatchCache actualMatchCache;
    private final FloorRemover floorRemover;
    private final Plugin plugin;
    private final FloorCooldownChecker floorCooldownChecker;
    private final Map<Location, Long> delayedLocations = new ConcurrentHashMap<>();

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            try {

                Optional<Match> matchOptional = actualMatchCache.get(player.getDatabaseIdentifier());

                if (!matchOptional.isPresent()) {
                    continue;
                }

                if (
                        matchOptional.get().getStatus() != MatchDoc.Status.RUNNING &&
                        MatchParticipantsProvider.getInvolvedIds(matchOptional.get()).contains(player.getDatabaseIdentifier())
                ) {
                    continue;
                }

                if (floorCooldownChecker.hasCooldown(matchOptional.get().getId())) {
                    continue;
                }

                if (!delayedLocations.containsKey(player.getLocation())) {
                    delayedLocations.put(player.getLocation(), System.currentTimeMillis());
                }

            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "There was an error while checking match floor removal", e);
            }
        }

        for (Map.Entry<Location, Long> entry : new ArrayList<>(delayedLocations.entrySet())) {
            if (entry.getValue() + DELAY_MS <= System.currentTimeMillis()) {
                floorRemover.removeFloor(entry.getKey());
                delayedLocations.remove(entry.getKey());
            }
        }
    }
}
