package net.astrocube.tnt.podium;

import java.util.Date;

public class CoreParticipant implements MatchProgress.Participant {
    private Date disqualificationDate;
    private final String playerId;

    public CoreParticipant(String playerId) {
        disqualificationDate = null;
        this.playerId = playerId;
    }

    @Override
    public Date getDisqualificationDate() {
        return disqualificationDate;
    }

    @Override
    public void setDisqualificationDate(Date date) {
        this.disqualificationDate = date;
    }

    @Override
    public String getPlayerId() {
        return playerId;
    }

}
