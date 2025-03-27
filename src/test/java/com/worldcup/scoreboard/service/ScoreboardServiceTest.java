package com.worldcup.scoreboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.worldcup.scoreboard.model.Match;
import com.worldcup.scoreboard.model.Team;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ScoreboardService} class.
 * <p>
 * Most test methods are self-explanatory by their names, following the convention of describing
 * the expected behavior or scenario being tested. Each test focuses on a specific aspect of the
 * {@link ScoreboardService}, such as starting a match, score update, finishing match and getting summary.
 */
class ScoreboardServiceTest {

    private ScoreboardService scoreboard;
    private Team homeTeam;
    private Team awayTeam;

    @BeforeEach
    void setUp() {
        scoreboard = new ScoreboardService();
        homeTeam = new Team("HomeTeam");
        awayTeam = new Team("AwayTeam");
    }

    @Test
    void startMatchShouldCreateNewMatchAndAddToScoreboard() {
        // Act
        Match match = scoreboard.startMatch(homeTeam, awayTeam);

        // Assert
        assertNotNull(match, "Created match should not be null");
        assertEquals(homeTeam, match.getHomeTeam(), "Home team should match");
        assertEquals(awayTeam, match.getAwayTeam(), "Away team should match");
        assertEquals(0, match.getHomeScore(), "Initial home score should be 0");
        assertEquals(0, match.getAwayScore(), "Initial away score should be 0");
        assertTrue(match.isInProgress(), "Match should be in progress");

        List<Match> matches = scoreboard.getMatches();
        assertEquals(1, matches.size(), "Scoreboard should contain one match");
        assertTrue(matches.contains(match), "Scoreboard should contain the created match");
    }


    @Test
    void updateScoreShouldChangeMatchScores() {
        // Arrange
        Match match = scoreboard.startMatch(homeTeam, awayTeam);
        int newHomeScore = 2;
        int newAwayScore = 1;

        // Act
        scoreboard.updateScore(match, newHomeScore, newAwayScore);

        // Assert
        assertEquals(newHomeScore, match.getHomeScore(), "Home score should be updated");
        assertEquals(newAwayScore, match.getAwayScore(), "Away score should be updated");
    }

    @Test
    void updateScoreShouldRejectMatchNotInScoreboard() {
        // Arrange
        Match match = new Match(homeTeam, awayTeam); // Not added to scoreboard

        // Assert
        assertThrows(IllegalStateException.class, () -> scoreboard.updateScore(match, 1, 1),
                "Should reject match not in scoreboard");
    }

    @Test
    void updateScoreShouldRejectFinishedMatch() {
        // Arrange
        Match match = scoreboard.startMatch(homeTeam, awayTeam);
        scoreboard.finishMatch(match);

        // Assert
        assertThrows(IllegalStateException.class, () -> scoreboard.updateScore(match, 1, 1),
                "Should reject finished match");
    }

    @Test
    void finishMatchShouldRejectNullMatch() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> scoreboard.finishMatch(null),
                "Should reject null match");
    }

    @Test
    void finishMatchShouldRejectMatchNotInScoreboard() {
        // Arrange - Create a match but don't add it to scoreboard
        Match externalMatch = new Match(new Team("External"), new Team("Match"));

        // Act & Assert
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> scoreboard.finishMatch(externalMatch),
                "Should reject match not in scoreboard"
        );

        assertEquals("Match not found on the scoreboard", exception.getMessage());
    }

    @Test
    void getSummaryShouldReturnMatchesOrderedByTotalScoreAndThenByStartTime() {
        // Arrange - Creating matches with different scores and start times
        Team mexico = new Team("Mexico");
        Team canada = new Team("Canada");
        Match match1 = scoreboard.startMatch(mexico, canada);
        match1.updateScore(0, 5); // Total: 5

        Team spain = new Team("Spain");
        Team brazil = new Team("Brazil");
        Match match2 = scoreboard.startMatch(spain, brazil);
        match2.updateScore(10, 2); // Total: 12

        Team germany = new Team("Germany");
        Team france = new Team("France");
        Match match3 = scoreboard.startMatch(germany, france);
        match3.updateScore(2, 2); // Total: 4

        Team uruguay = new Team("Uruguay");
        Team italy = new Team("Italy");
        Match match4 = scoreboard.startMatch(uruguay, italy);
        match4.updateScore(6, 6); // Total: 12

        Team argentina = new Team("Argentina");
        Team australia = new Team("Australia");
        Match match5 = scoreboard.startMatch(argentina, australia);
        match5.updateScore(3, 1); // Total: 4

        // Act
        String summary = scoreboard.getSummary();

        // Assert
        String expectedSummary = """
                Matches summary:
                1. Uruguay 6 - Italy 6
                2. Spain 10 - Brazil 2
                3. Mexico 0 - Canada 5
                4. Argentina 3 - Australia 1
                5. Germany 2 - France 2
                """;

        assertEquals(expectedSummary, summary, "Summary should match the expected format and order");
    }

    @Test
    void getSummaryShouldReturnOnlyInProgressMatches() {
        // Arrange
        Match match1 = scoreboard.startMatch(new Team("Team1"), new Team("Team2"));
        scoreboard.startMatch(new Team("Team3"), new Team("Team4"));

        // Finish match1
        scoreboard.finishMatch(match1);


        // Act
        String summary = scoreboard.getSummary();

        // Assert
        String expectedSummary = "Matches summary:\n" + // Add this prefix
                "1. Team3 0 - Team4 0\n"; // Assuming initial scores are 0-0

        assertEquals(expectedSummary, summary, "Summary should contain only in-progress matches");
    }

    @Test
    void getMatchesShouldReturnDefensiveCopy() {
        // Arrange
        scoreboard.startMatch(homeTeam, awayTeam);

        // Act
        List<Match> matches = scoreboard.getMatches();

        // Assert
        assertNotNull(matches, "Returned list should not be null");
        assertEquals(1, matches.size(), "List should contain one match");

        // Try to modify the returned list
        matches.clear();

        // The scoreboard's internal list should not be affected
        assertEquals(1, scoreboard.getMatches().size(),
                "Modifying returned list should not affect the scoreboard's internal state");
    }

    @Test
    void updateScoreShouldRejectNegativeScores() {
        // Arrange
        Match match = scoreboard.startMatch(homeTeam, awayTeam);

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> scoreboard.updateScore(match, -1, 0),
                "Should reject negative home score");

        assertThrows(IllegalArgumentException.class,
                () -> scoreboard.updateScore(match, 0, -1),
                "Should reject negative away score");
    }

    @Test
    void getSummaryShouldHandleEmptyScoreboard() {
        // Act
        String summary = scoreboard.getSummary();

        // Assert
        assertEquals("Matches summary:\n", summary,
                "Should return empty summary for empty scoreboard");
    }

    @Test
    void startMatchShouldPreventDuplicateTeams() {
        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> scoreboard.startMatch(homeTeam, homeTeam),
                "Should reject match with same team for home and away");
    }

    @Test
    void finishMatchShouldBeIdempotent() {
        // Arrange
        Match match = scoreboard.startMatch(homeTeam, awayTeam);

        // Act - First finish
        scoreboard.finishMatch(match);

        // Assert - Second finish should be ignored
        assertDoesNotThrow(() -> scoreboard.finishMatch(match),
                "Subsequent finish calls should be ignored");

        assertEquals(0, scoreboard.getMatches().size(),
                "Scoreboard should remain empty after duplicate finish");
    }

}