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
				.sorted(
						Comparator.comparing(
								MatchProgress.Participant::getDisqualificationDate,
								Comparator.nullsLast(Comparator.reverseOrder())
						).reversed()
				)
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

		for (MatchProgress progress : disqualifications) {

			if (progress.getMatch().equalsIgnoreCase(match)) {

				Set<MatchProgress.Participant> participants = progress.getDisqualifiedPlayers()
						.stream().peek(participant -> {
							if (participant.getPlayerId().equalsIgnoreCase(id)) {
								participant.setDisqualificationDate(new Date());
							}
						}).collect(Collectors.toSet());

				progress.setDisqualifiedPlayers(participants);

				return;

			}

		}

	}

	@Override
	public void registerMatch(String match, Set<String> participants) {
		disqualifications.add(new CoreProgressHandler(participants.stream().map(CoreParticipant::new).collect(Collectors.toSet()), match));
	}


}
