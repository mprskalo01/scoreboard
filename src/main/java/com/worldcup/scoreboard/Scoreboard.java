package com.worldcup.scoreboard;

import com.worldcup.scoreboard.model.Team;
import com.worldcup.scoreboard.model.Match;
import com.worldcup.scoreboard.service.ScoreboardService;

/**
 * Public API for the World Cup Scoreboard.
 * <p>
 * This class provides a simple API for managing football matches on a scoreboard,
 * including starting matches, updating scores, finishing matches, and getting summaries.
 * <p>
 * Example usage:
 * <pre>
 * Scoreboard scoreboard = new Scoreboard();
 *
 * Match match = scoreboard.startMatch("Mexico", "Canada");
 *
 * scoreboard.updateScore(match, 0, 5);
 *
 * String summary = scoreboard.getSummary();
 *
 * scoreboard.finishMatch(match);
 * </pre>
 */
public class Scoreboard {
    private final ScoreboardService scoreboardService;

    /**
     * Constructs a new Scoreboard instance.
     */
    public Scoreboard() {
        this.scoreboardService = new ScoreboardService();
    }

    /**
     * Starts a new match between two teams (specified by name) and adds it to the scoreboard.
     * The match starts with a score of 0-0.
     *
     * @param homeTeamName the home team name (must not be null or empty)
     * @param awayTeamName the away team name (must not be null or empty)
     * @return the created match
     * @throws IllegalArgumentException if either team name is invalid
     */
    public Match startMatch(String homeTeamName, String awayTeamName) {
        Team homeTeam = new Team(homeTeamName);
        Team awayTeam = new Team(awayTeamName);
        return scoreboardService.startMatch(homeTeam, awayTeam);
    }

    /**
     * Updates the score for an in-progress match.
     *
     * @param match the match to update (must not be null)
     * @param homeScore new home team score (must not be negative)
     * @param awayScore new away team score (must not be negative)
     * @throws IllegalArgumentException if match is null or scores are negative
     * @throws IllegalStateException if match is not in progress or not found on the scoreboard
     */
    public void updateScore(Match match, int homeScore, int awayScore) {
        scoreboardService.updateScore(match, homeScore, awayScore);
    }

    /**
     * Finishes and removes a match from the scoreboard.
     *
     * @param match the match to finish (must not be null)
     * @throws IllegalArgumentException if match is null
     * @throws IllegalStateException if match is not found on the scoreboard
     */
    public void finishMatch(Match match) {
        scoreboardService.finishMatch(match);
    }

    /**
     * Gets a summary of all matches in progress, ordered by:
     * 1. Total score (descending)
     * 2. Most recently started match (if scores are equal)
     *
     * @return formatted summary string of matches in progress
     */
    public String getSummary() {
        return scoreboardService.getSummary();
    }
}