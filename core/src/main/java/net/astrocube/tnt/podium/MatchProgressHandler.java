package net.astrocube.tnt.podium;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MatchProgressHandler {

	/**
	 * @param match to check
	 * @return three winners of the podium
	 */
	List<MatchProgress.Participant> getPodium(String match);

	/**
	 * Clears match from registry
	 * @param match to clear
	 */
	void clearMatch(String match);

	/**
	 * @param match to check
	 * @return optional containing progress
	 */
	Optional<MatchProgress> getMatchProgress(String match);


	/**
	 * Disqualify player from match.
	 * @param match of match belonging to user
	 * @param id    of user to disqualify
	 */
	void disqualify(String match, String id);

	/**
	 * Register match at progress handler
	 * @param participants who started
	 * @param match        to register
	 */
	void registerMatch(String match, Set<String> participants);

}
