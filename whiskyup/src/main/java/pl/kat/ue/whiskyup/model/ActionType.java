package pl.kat.ue.whiskyup.model;

import java.util.Arrays;
import java.util.Optional;

public enum ActionType {

    CREATE("create"),
    DELETE("delete");

    private final String label;

    ActionType(String label) {
        this.label = label;
    }

    public static ActionType valueOfLabel(String label) {
        return Optional.ofNullable(label)
                .map(l -> Arrays.stream(values())
                        .filter(type -> type.label.equals(l.toLowerCase()))
                        .findFirst()
                        .orElseGet(null)
                )
                .orElse(null);
    }
}