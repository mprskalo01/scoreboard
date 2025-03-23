package scoreboard.model;

import java.util.stream.Collectors;
import java.util.stream.Stream;

// immutable record class
public record Team(String name) {
    static final int MINIMUM_NAME_LENGTH = 3;

    public Team {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        name = formatName(name); // Format the name
        if (name.length() < MINIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException("Name must be at least " + MINIMUM_NAME_LENGTH + " characters long.");
        }
    }

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

    @Override
    public String toString() {
        return name;
    }

}