package net.astrocube.tnt.lobby.statistic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.query.QueryService;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

@Singleton
public class CoreModeVictoryProvider implements ModeVictoryProvider {

    private @Inject QueryService<Match> queryService;
    private @Inject ObjectMapper mapper;
    private @Inject Plugin plugin;

    @Override
    public int getWonMatches(String mode, String player)  {

        ObjectNode nodes = mapper.createObjectNode();

        String gameMode = plugin.getConfig().getString("registry.mode");

        if (gameMode == null || gameMode.equalsIgnoreCase("")) {
            return 0;
        }

        nodes.put("winner", player);
        nodes.put("gamemode", gameMode);
        nodes.put("subGamemode", mode);

        try {
            return queryService.querySync(nodes).getFoundModels().size();
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error while retrieving won matches", e);
            return -1;
        }
    }

}
