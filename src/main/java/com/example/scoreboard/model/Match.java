package com.example.scoreboard.model;

import java.time.LocalDateTime;

/**
 * Represents a match between two teams, keeping track of the score and match status.
 */
public class Match {
    private final Team homeTeam;
    private final Team awayTeam;
    private int homeScore;
    private int awayScore;
    private final LocalDateTime startTime;
    private boolean inProgress;

    /**
     * Constructs a new match between the specified home and away teams.
     * The match starts with a score of 0-0 and is marked as in progress.
     *
     * @param homeTeam the home team (must not be null)
     * @param awayTeam the away team (must not be null)
     * @throws IllegalArgumentException if either team is null
     */
    public Match(Team homeTeam, Team awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Teams must not be null");
        }
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.startTime = LocalDateTime.now();
        this.inProgress = true;
    }

    /**
     * Returns the home team.
     *
     * @return the home team
     */
    public Team getHomeTeam() {
        return homeTeam;
    }

    /**
     * Returns the away team.
     *
     * @return the away team
     */
    public Team getAwayTeam() {
        return awayTeam;
    }

    /**
     * Returns the current score of the home team.
     *
     * @return the home team's score
     */
    public int getHomeScore() {
        return homeScore;
    }

    /**
     * Returns the current score of the away team.
     *
     * @return the away team's score
     */
    public int getAwayScore() {
        return awayScore;
    }

    /**
     * Returns the start time of the match.
     *
     * @return the start time of the match
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Checks if the match is currently in progress.
     *
     * @return true if the match is in progress, false otherwise
     */
    public boolean isInProgress() {
        return inProgress;
    }

    /**
     * Updates the score of the match.
     *
     * @param homeScore the new score for the home team (must not be negative)
     * @param awayScore the new score for the away team (must not be negative)
     * @throws IllegalStateException if the match is not in progress
     * @throws IllegalArgumentException if either score is negative
     */
    public void updateScore(int homeScore, int awayScore) {
        if (!inProgress) {
            throw new IllegalStateException("Cannot update score for a finished match");
        }
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    /**
     * Marks the match as finished.
     */
    public void finishMatch() {
        this.inProgress = false;
    }

    /**
     * Returns the total score of the match (sum of home and away scores).
     *
     * @return the total score of the match
     */
    public int getTotalScore() {
        return homeScore + awayScore;
    }

    /**
     * Returns a string representation of the match in the format:
     * "[Home Team] [Home Score] - [Away Team] [Away Score]".
     *
     * @return a string representation of the match
     */
    @Override
    public String toString() {
        return homeTeam.name() + " " + homeScore + " - " + awayTeam.name() + " " + awayScore;
    }

    public String getMatch() {
        return homeTeam.name() + " " + homeScore + " - " + awayTeam.name() + " " + awayScore;
    }

}