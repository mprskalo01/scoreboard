package com.example.scoreboard.comparator;

import com.example.scoreboard.model.Match;

import java.util.Comparator;
import java.util.List;

/**
 * A comparator for sorting {@link Match} objects.
 * Matches are first compared by their total score in descending order.
 * If two matches have the same total score, they are compared by their insertion order
 * in the provided list, with the most recently added match coming first.
 */
public class MatchComparator implements Comparator<Match> {
    private final List<Match> matches;

    /**
     * Constructs a MatchComparator with the provided list of matches.
     *
     * @param matches the list of matches to be used for determining insertion order
     */
    public MatchComparator(List<Match> matches) {
        this.matches = matches;
    }

    /**
     * Compares two matches based on their total score (descending) and insertion order.
     *
     * @param match1 the first match to compare
     * @param match2 the second match to compare
     * @return a negative integer, zero, or a positive integer if the first match is
     *         less than, equal to, or greater than the second match, respectively
     */
    @Override
    public int compare(Match match1, Match match2) {
        // First compare by total score (descending)
        int scoreComparison = Integer.compare(match2.getTotalScore(), match1.getTotalScore());

        if (scoreComparison != 0) {
            return scoreComparison;
        }

        // If scores are equal, compare by insertion order (most recently added first)
        return Integer.compare(matches.indexOf(match2), matches.indexOf(match1));
    }
}