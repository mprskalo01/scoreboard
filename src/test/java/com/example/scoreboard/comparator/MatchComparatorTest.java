
package com.example.scoreboard.comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.scoreboard.model.Match;
import com.example.scoreboard.model.Team;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link MatchComparator} class.
 * <p>
 * Tests validate that matches are correctly sorted by total score (descending) and,
 * if scores are equal, by insertion order (most recent first).
 * Includes cases for different scores, equal scores, and edge cases.
 */
class MatchComparatorTest {
    private MatchComparator comparator;
    private List<Match> matches;
    private Match match1;
    private Match match2;
    private Match match3;

    @BeforeEach
    void setUp() {
        // Arrange - initialize test data
        matches = new ArrayList<>();
        Team homeTeam1 = new Team("Home1");
        Team awayTeam1 = new Team("Away1");
        Team homeTeam2 = new Team("Home2");
        Team awayTeam2 = new Team("Away2");
        Team homeTeam3 = new Team("Home3");
        Team awayTeam3 = new Team("Away3");

        match1 = new Match(homeTeam1, awayTeam1);
        match2 = new Match(homeTeam2, awayTeam2);
        match3 = new Match(homeTeam3, awayTeam3);

        matches.add(match1);
        matches.add(match2);
        matches.add(match3);

        comparator = new MatchComparator(matches);
    }

    @Test
    void compareShouldOrderByHigherTotalScoreFirst() {
        // Arrange - set up match scores
        match1.updateScore(3, 2); // Total: 5
        match2.updateScore(1, 1); // Total: 2

        // Act & Assert - verify comparison result

        assertTrue(comparator.compare(match1, match2) < 0,
                "Higher scoring match should come first");
    }

    @Test
    void compareShouldOrderByInsertionOrderIfScoresAreEqual() {
        // Arrange - set up matches with equal scores
        match1.updateScore(2, 1); // Total: 3 (added first)
        match2.updateScore(1, 2); // Total: 3 (added second)

        // Act & Assert - newer match (match2) should come first
        assertTrue(comparator.compare(match1, match2) > 0,
                "When scores are equal, newer match should come first");
    }

    @Test
    void compareShouldReturnZeroForSameMatch() {
        // Act & Assert - comparing a match with itself should return 0
        assertEquals(0, comparator.compare(match1, match1),
                "Comparing the same match should return 0");
    }

    @Test
    void compareShouldHandleMultipleMatchesCorrectly() {
        // Arrange - set up three matches with different scenarios
        match1.updateScore(0, 0); // Lowest score, added first
        match2.updateScore(3, 2); // High score, added second
        match3.updateScore(4, 1); // Same high score as match2, added third

        // Assert - verify complex ordering logic

        // Case 1: Same score, newer match comes first
        // match3 vs match2 - both have score 5, match3 is newer
        assertTrue(comparator.compare(match2, match3) > 0,
                "When scores are equal, newer match should come first");

        // Case 2: Different scores, higher score comes first
        // match2 vs match1 - match2 has higher score
        assertTrue(comparator.compare(match2, match1) < 0,
                "Higher scoring match should come first");
    }
}