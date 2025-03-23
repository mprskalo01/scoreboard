package scoreboard.model;

import java.util.Objects;


public record Team(String name) {
    static final int MINIMUM_NAME_LENGTH = 3;

    public Team {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        if (name.length() < MINIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException("Name must be at least" + MINIMUM_NAME_LENGTH + "characters long.");
        }
    }

}