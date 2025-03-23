package scoreboard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Match} class.
 * <p>
 * Most test methods are self-explanatory by their names, following the convention of describing
 * the expected behavior or scenario being tested. Each test focuses on a specific aspect of the
 * {@link Match} class, such as initialization, score updates, match state, and edge cases.
 */
class MatchTest {

    private Team homeTeam;
    private Team awayTeam;
    private Match match;

    @BeforeEach
    void setUp() {
        homeTeam = new Team("Spain");
        awayTeam = new Team("Brazil");
        match = new Match(homeTeam, awayTeam);
    }

    @Test
    void constructorShouldInitializeWithTeamsAndZeroScores() {
        // Assert
        assertAll(
                () -> assertEquals(homeTeam, match.getHomeTeam(), "Home team should be initialized correctly"),
                () -> assertEquals(awayTeam, match.getAwayTeam(), "Away team should be initialized correctly"),
                () -> assertEquals(0, match.getHomeScore(), "Initial home score should be 0"),
                () -> assertEquals(0, match.getAwayScore(), "Initial away score should be 0"),
                () -> assertTrue(match.isInProgress(), "Match should be in progress when created"),
                () -> assertNotNull(match.getStartTime(), "Start time should be initialized")
        );
    }

    @Test
    void constructorShouldRejectNullHomeTeam() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Match(null, awayTeam),
                "Constructor should reject null home team");
    }

    @Test
    void constructorShouldRejectNullAwayTeam() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Match(homeTeam, null),
                "Constructor should reject null away team");
    }

    @Test
    void constructorShouldRejectBothNullTeams() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Match(null, null),
                "Constructor should reject both null teams");
    }

    @Test
    void updateScoreShouldChangeScores() {
        // Arrange
        int newHomeScore = 2;
        int newAwayScore = 1;

        // Act
        match.updateScore(newHomeScore, newAwayScore);

        // Assert
        assertEquals(newHomeScore, match.getHomeScore(), "Home score should be updated");
        assertEquals(newAwayScore, match.getAwayScore(), "Away score should be updated");
    }

    @Test
    void updateScoreShouldRejectNegativeHomeScore() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> match.updateScore(-1, 0),
                "updateScore should throw IllegalArgumentException when home score is negative");
    }

    @Test
    void updateScoreShouldRejectNegativeAwayScore() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> match.updateScore(0, -1),
                "updateScore should throw IllegalArgumentException when away score is negative");
    }

    @Test
    void updateScoreShouldThrowWhenMatchIsFinished() {
        // Arrange
        match.finishMatch();

        // Assert
        assertThrows(IllegalStateException.class, () -> match.updateScore(1, 1),
                "updateScore should throw IllegalStateException when match is finished");
    }

    @Test
    void finishMatchShouldSetInProgressToFalse() {
        // Act
        match.finishMatch();

        // Assert
        assertFalse(match.isInProgress(), "Match should not be in progress after finishing");
    }

    @Test
    void getTotalScoreShouldReturnSumOfScores() {
        // Arrange
        match.updateScore(3, 2);

        // Act
        int totalScore = match.getTotalScore();

        // Assert
        assertEquals(5, totalScore, "Total score should be the sum of home and away scores");
    }

    @Test
    void getMatchShouldContainTeamNamesAndScores() {
        // Arrange
        match.updateScore(3, 1);
        String expected = "Spain 3 - Brazil 1";

        // Act
        String result = match.getMatch();

        // Assert
        assertEquals(expected, result, "getMatch should return formatted match result");
    }

    @Test
    void toStringShouldContainTeamNamesAndScores() {
        // Arrange
        match.updateScore(3, 1);
        String expected = "Spain 3 - Brazil 1";

        // Act
        String result = match.toString();

        // Assert
        assertEquals(expected, result, "toString should return formatted match result");
    }

    @Test
    void startTimeShouldBeSetToCurrentTimeWhenCreated() {
        // Arrange
        LocalDateTime beforeCreation = LocalDateTime.now().minusSeconds(1);

        // Act
        Match newMatch = new Match(homeTeam, awayTeam);
        LocalDateTime afterCreation = LocalDateTime.now().plusSeconds(1);

        // Assert
        assertTrue(
                !newMatch.getStartTime().isBefore(beforeCreation) &&
                        !newMatch.getStartTime().isAfter(afterCreation),
                "Start time should be set to current time when match is created"
        );
    }
}