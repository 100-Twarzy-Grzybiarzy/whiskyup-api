package pl.kat.ue.whiskyup.model;

import java.util.Arrays;
import java.util.Objects;

public enum ActionType {

    CREATE("create"),
    DELETE("delete");

    private final String label;

    ActionType(String label) {
        this.label = label;
    }

    public static ActionType valueOfLabel(String label) {
        if (Objects.isNull(label)) {
            return null;
        }
        return Arrays.stream(values())
                .filter(actionType -> actionType.label.equals(label.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
}