package net.astrocube.tnt.podium;

import java.util.Date;
import java.util.Set;

public interface MatchProgress {

    /**
     * @return date where match started.
     */
    Date getStartDate();

    /**
     * @return set of disqualified players.
     */
    Set<Participant> getDisqualifiedPlayers();

    /**
     * @return match where player was disqualified
     */
    String getMatch();

    /**
     * Wrapper providing every disqualified player
     */
    interface Participant {

        /**
         * @return disqualification date of the player
         */
        Date getDisqualificationDate();

        /**
         * Updates disqualification date.
         * @param date to update
         */
        void setDisqualificationDate(Date date);

        /**
         * @return get player who got disqualified
         */
        String getPlayerId();

        boolean isAlive();

    }

}
