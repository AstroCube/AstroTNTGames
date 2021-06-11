package net.astrocube.tnt.podium;

import java.util.Date;

public class CoreParticipant implements MatchProgress.Participant {
	private final String playerId;
	private Date disqualificationDate;

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
