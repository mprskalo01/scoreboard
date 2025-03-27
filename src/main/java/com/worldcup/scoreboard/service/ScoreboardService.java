package com.worldcup.scoreboard.service;

import java.util.ArrayList;
import java.util.List;
import com.worldcup.scoreboard.model.Match;
import com.worldcup.scoreboard.model.Team;
import com.worldcup.scoreboard.comparator.MatchComparator;

/**
 * Service for managing football match scoreboard operations.
 * <p>
 * Maintains a collection of matches and provides methods to start, update,
 * and finish matches, with validation for business rules.
 */
public class ScoreboardService {
    private final List<Match> matches;
    /**
     * Constructs an empty scoreboard service.
     */
    public ScoreboardService() {
        this.matches = new ArrayList<>();
    }

    /**
     * Starts a new match between two different teams.
     *
     * @param homeTeam the home team (non-null)
     * @param awayTeam the away team (non-null)
     * @return the created match
     * @throws IllegalArgumentException if either team is null
     * @throws IllegalArgumentException if both teams are the same
     */
    public Match startMatch(Team homeTeam, Team awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Teams cannot be null");
        }

        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Home and away teams cannot be the same");
        }

        Match match = new Match(homeTeam, awayTeam);
        matches.add(match);
        return match;
    }

    /**
     * Updates the score for an in-progress match.
     *
     * @param match the match to update (non-null)
     * @param homeScore new home team score
     * @param awayScore new away team score
     * @throws IllegalArgumentException if match is null
     * @throws IllegalStateException if match is not in progress or not found
     */
    public void updateScore(Match match, int homeScore, int awayScore) {
        if (match == null) {
            throw new IllegalArgumentException("Match cannot be null");
        }

        if (!matches.contains(match) || !match.isInProgress()) {
            throw new IllegalStateException("Match is not in progress or not found on the scoreboard");
        }

        match.updateScore(homeScore, awayScore);
    }

    /**
     * Finishes and removes a match from the scoreboard.
     *
     * @param match the match to finish (non-null)
     * @throws IllegalArgumentException if match is null
     * @throws IllegalStateException if match is not found
     */
    public void finishMatch(Match match) {
        if (match == null) {
            throw new IllegalArgumentException("Match cannot be null");
        }

        if (!matches.contains(match)) {
            // Check if this match was previously in the scoreboard (finished)
            if (match.isInProgress()) {
                // Match was never in the scoreboard
                throw new IllegalStateException("Match not found on the scoreboard");
            }
            // Match was already finished - idempotent case
            return;
        }

        match.finishMatch();
        matches.remove(match);  // Remove from scoreboard
    }

    /**
     * Generates a summary of in-progress matches sorted by:
     * 1. Total score (descending)
     * 2. Most recently started (if scores equal)
     *
     * @return formatted summary string
     */
    public String getSummary() {
        StringBuilder summary = new StringBuilder("Matches summary:\n");

        List<Match> sortedMatches = matches.stream()
                .filter(Match::isInProgress)
                .sorted(new MatchComparator(matches))
                .toList();

        for (int i = 0; i < sortedMatches.size(); i++) {
            Match match = sortedMatches.get(i);
            summary.append(i + 1).append(". ")
                    .append(match.getHomeTeam()).append(" ")
                    .append(match.getHomeScore()).append(" - ")
                    .append(match.getAwayTeam()).append(" ")
                    .append(match.getAwayScore())
                    .append("\n");
        }

        return summary.toString();
    }

    /**
     * Gets a defensive copy of all matches for usage.
     *
     * @return new list containing all matches
     */
    public List<Match> getMatches() {
        return new ArrayList<>(matches);
    }
}