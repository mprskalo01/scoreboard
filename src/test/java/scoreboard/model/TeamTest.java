package scoreboard.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    void constructorShouldSetTeamName() {
        String expectedName = "Brazil";
        Team team = new Team(expectedName);
        assertEquals(expectedName, team.name(), "Team name should match the one provided in constructor");
    }

    @Test
    void constructorShouldRejectNullName() {
        assertThrows(IllegalArgumentException.class, () -> new Team(null),
                "Constructor should throw IllegalArgumentException when name is null");
    }

    @Test
    void constructorShouldRejectEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new Team(""),
                "Constructor should throw IllegalArgumentException when name is empty");
    }

    @Test
    void constructorShouldRejectShortNames() {
        assertThrows(IllegalArgumentException.class, () -> new Team("AB"),
                "Constructor should throw IllegalArgumentException when name is shorter than set characters");
    }

    @Test
    void equalTeamsShouldHaveSameHashCode() {
        Team team1 = new Team("Spain");
        Team team2 = new Team("Spain");
        assertEquals(team1.hashCode(), team2.hashCode(),
                "Equal teams should have the same hash code");
    }

    @Test
    void differentTeamsShouldHaveDifferentHashCodes() {
        Team team1 = new Team("Brazil");
        Team team2 = new Team("Argentina");
        assertNotEquals(team1.hashCode(), team2.hashCode(),
                "Teams with different names should have different hash codes");
    }

    @Test
    void equalsShouldReturnTrueForSameTeamName() {
        Team team1 = new Team("Germany");
        Team team2 = new Team("Germany");
        assertEquals(team1, team2, "Teams with the same name should be equal");
    }

    @Test
    void equalsShouldReturnFalseForDifferentTeamNames() {
        Team team1 = new Team("France");
        Team team2 = new Team("Italy");
        assertNotEquals(team1, team2, "Teams with different names should not be equal");
    }

    @Test
    void nameShouldReturnTeamName() {
        String teamName = "Argentina";
        Team team = new Team(teamName);
        String result = team.name();
        assertEquals(teamName, result, "Getter method name should return the team name");
    }

    @Test
    void constructorShouldFormatNameCorrectly() {
        // Test various formatting scenarios
        assertEquals("San Marino", new Team("san marino").name(), "Name should be formatted correctly");
        assertEquals("San Marino", new Team("SAN MARINO").name(), "Name should be formatted correctly");
        assertEquals("San Marino", new Team("sAn mArInO").name(), "Name should be formatted correctly");
        assertEquals("San Marino", new Team("  san marino  ").name(), "Name should be trimmed and formatted correctly");
        assertEquals("New Zealand", new Team("new zealand").name(), "Name should be formatted correctly");
    }

    @Test
    void constructorShouldAcceptNonAsciiCharacters() {
        String name = "España";
        Team team = new Team(name);
        assertEquals(name, team.name(), "Constructor should accept names with non-ASCII characters");
    }

    @Test
    void toStringShouldReturnTeamName() {
        String teamName = "Argentina";
        Team team = new Team(teamName);
        assertEquals(teamName, team.toString(), "toString should return the team name");
    }
}