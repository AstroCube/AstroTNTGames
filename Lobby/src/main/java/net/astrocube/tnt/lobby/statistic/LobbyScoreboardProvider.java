package net.astrocube.tnt.lobby.statistic;

import org.bukkit.entity.Player;

public interface LobbyScoreboardProvider {

    /**
     * Setup scoreboard for player on join.
     * @param player to setup
     */
    void setup(Player player);

}
