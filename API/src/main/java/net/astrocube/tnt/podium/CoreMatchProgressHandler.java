package net.astrocube.tnt.podium;

import com.google.inject.Singleton;

import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class CoreMatchProgressHandler implements MatchProgressHandler {

    private final Set<MatchProgress> disqualifications = new HashSet<>();

    @Override
    public List<MatchProgress.Participant> getPodium(String match) {

        Optional<MatchProgress> progressOptional =
                disqualifications.stream().filter(progress -> progress.getMatch().equalsIgnoreCase(match)).findAny();

        return progressOptional.map(matchProgress -> matchProgress.getDisqualifiedPlayers()
                .stream()
                .sorted(Comparator.comparing(MatchProgress.Participant::getDisqualificationDate))
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public void clearMatch(String match) {
        disqualifications.removeIf(matchProgress -> matchProgress.getMatch().equalsIgnoreCase(match));
    }

    @Override
    public Optional<MatchProgress> getMatchProgress(String match) {
        return disqualifications.stream().filter(m -> m.getMatch().equalsIgnoreCase(match)).findAny();
    }

    @Override
    public void disqualify(String match, String id) {

        Optional<MatchProgress> progress = getMatchProgress(match);

        progress.ifPresent(matchProgress ->
                matchProgress.getDisqualifiedPlayers().forEach(player -> {
                    if (player.getDisqualificationDate() == null) {
                        player.setDisqualificationDate(new Date());
                    }
                })
        );

    }

    @Override
    public void registerMatch(String match, Set<String> participants) {
        disqualifications.add(new MatchProgress() {
            @Override
            public Date getStartDate() {
                return new Date();
            }

            @Override
            public Set<Participant> getDisqualifiedPlayers() {
                return participants.stream().map(id -> new Participant() {

                    private Date date;

                    @Override
                    public Date getDisqualificationDate() {
                        return date;
                    }

                    @Override
                    public void setDisqualificationDate(Date date) {
                        this.date = date;
                    }

                    @Override
                    public String getPlayerId() {
                        return id;
                    }

                    @Override
                    public boolean isAlive() {
                        return true;
                    }
                }).collect(Collectors.toSet());
            }

            @Override
            public String getMatch() {
                return match;
            }
        });
    }


}
