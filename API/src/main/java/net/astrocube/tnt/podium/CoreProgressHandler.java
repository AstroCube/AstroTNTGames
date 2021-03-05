package net.astrocube.tnt.podium;

import net.seocraft.lib.netty.util.internal.ConcurrentSet;

import java.util.Date;
import java.util.Set;

public class CoreProgressHandler implements MatchProgress {

    private final Date startDate;
    private Set<Participant> disqualifiedPlayers;
    private final String match;

    public CoreProgressHandler(Set<Participant> disqualifiedPlayers, String match) {
        this.startDate = new Date();
        this.disqualifiedPlayers = new ConcurrentSet<>();
        this.disqualifiedPlayers.addAll(disqualifiedPlayers);
        this.match = match;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Set<Participant> getDisqualifiedPlayers() {
        return disqualifiedPlayers;
    }

    @Override
    public void setDisqualifiedPlayers(Set<Participant> participants) {
        this.disqualifiedPlayers = participants;
    }

    @Override
    public String getMatch() {
        return match;
    }
}
