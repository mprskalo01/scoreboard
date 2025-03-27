package com.worldcup.scoreboard.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Team} class.
 * <p>
 * Most test methods are self-explanatory by their names, following the convention of describing
 * the expected behavior or scenario being tested. Each test focuses on a specific aspect of the
 * {@link Team} class, such as validation, equality, formatting, and edge cases.
 */
class TeamTest {

    @Test
    void constructorShouldSetTeamName() {
        // Arrange
        String expectedName = "Brazil";

        // Act
        Team team = new Team(expectedName);

        // Assert
        assertEquals(expectedName, team.name(), "Team name should match the one provided in constructor");
    }

    @Test
    void constructorShouldRejectNullName() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Team(null),
                "Constructor should throw IllegalArgumentException when name is null");
    }

    @Test
    void constructorShouldRejectEmptyName() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Team(""),
                "Constructor should throw IllegalArgumentException when name is empty");
    }

    @Test
    void constructorShouldRejectShortNames() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Team("AB"),
                "Constructor should throw IllegalArgumentException when name is shorter than set characters");
    }

    @Test
    void equalTeamsShouldHaveSameHashCode() {
        // Arrange
        Team team1 = new Team("Spain");
        Team team2 = new Team("Spain");

        // Assert
        assertEquals(team1.hashCode(), team2.hashCode(),
                "Equal teams should have the same hash code");
    }

    @Test
    void differentTeamsShouldHaveDifferentHashCodes() {
        // Arrange
        Team team1 = new Team("Brazil");
        Team team2 = new Team("Argentina");

        // Assert
        assertNotEquals(team1.hashCode(), team2.hashCode(),
                "Teams with different names should have different hash codes");
    }

    @Test
    void equalsShouldReturnTrueForSameTeamName() {
        // Arrange
        Team team1 = new Team("Germany");
        Team team2 = new Team("Germany");

        // Assert
        assertEquals(team1, team2, "Teams with the same name should be equal");
    }

    @Test
    void equalsShouldReturnFalseForDifferentTeamNames() {
        // Arrange
        Team team1 = new Team("France");
        Team team2 = new Team("Italy");

        // Assert
        assertNotEquals(team1, team2, "Teams with different names should not be equal");
    }

    @Test
    void nameShouldReturnTeamName() {
        // Arrange
        String teamName = "Argentina";
        Team team = new Team(teamName);

        // Act
        String result = team.name();

        // Assert
        assertEquals(teamName, result, "Getter method name should return the team name");
    }

    @Test
    void constructorShouldFormatNameCorrectly() {
        // Assert
        assertAll(
                () -> assertEquals("San Marino", new Team("san marino").name(), "Name should be formatted correctly"),
                () -> assertEquals("San Marino", new Team("SAN MARINO").name(), "Name should be formatted correctly"),
                () -> assertEquals("San Marino", new Team("sAn mArInO").name(), "Name should be formatted correctly"),
                () -> assertEquals("San Marino", new Team("  san marino  ").name(), "Name should be trimmed and formatted correctly"),
                () -> assertEquals("New Zealand", new Team("new zealand").name(), "Name should be formatted correctly")
        );
    }

    @Test
    void constructorShouldAcceptNonAsciiCharacters() {
        // Arrange
        String name = "España";

        // Act
        Team team = new Team(name);

        // Assert
        assertEquals(name, team.name(), "Constructor should accept names with non-ASCII characters");
    }

    @Test
    void toStringShouldReturnTeamName() {
        // Arrange
        String teamName = "Argentina";
        Team team = new Team(teamName);

        // Assert
        assertEquals(teamName, team.toString(), "toString should return the team name");
    }
}