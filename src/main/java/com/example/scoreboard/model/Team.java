package com.example.scoreboard.model;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a team with a formatted name.
 * This is an immutable record class, meaning its state cannot be modified after creation.
 *
 * @param name the name of the team (must be at least {@value #MINIMUM_NAME_LENGTH} characters long)
 */
public record Team(String name) {
    /**
     * The minimum allowed length for a team name.
     */
    static final int MINIMUM_NAME_LENGTH = 3;

    /**
     * Constructs a new Team instance with the specified name.
     * The name is validated and formatted to ensure consistency.
     *
     * @param name the name of the team (must not be null or empty)
     * @throws IllegalArgumentException if the name is null, empty, or shorter than
     *                                  {@value #MINIMUM_NAME_LENGTH} characters
     */
    public Team {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        name = formatName(name); // Format the name
        if (name.length() < MINIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException("Name must be at least " + MINIMUM_NAME_LENGTH + " characters long.");
        }
    }

    /**
     * Formats the team name by trimming whitespace, converting it to lowercase,
     * and capitalizing the first letter of each word.
     *
     * @param name the raw name to format
     * @return the formatted name
     */
    private static String formatName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        // Trim and lowercase the entire string
        name = name.trim().toLowerCase();

        // Capitalize the first letter of each word
        return Stream.of(name.split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));
    }

    /**
     * Returns the formatted name of the team.
     *
     * @return the team's name
     */
    @Override
    public String toString() {
        return name;
    }
}