package net.astrocube.tnt.podium;

import org.bukkit.entity.Player;

import java.util.Date;

public interface MatchProgress {

    Date getStartDate();

    interface DisqualifiedPlayer {

        Date getDisqualificationDate();

        Player getPlayer();

    }

}
