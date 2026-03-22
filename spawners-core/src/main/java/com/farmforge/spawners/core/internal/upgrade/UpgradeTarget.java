package com.farmforge.spawners.core.internal.upgrade;

public enum UpgradeTarget {
    DROP_VALUE("Drop Value Multiplier"),
    CAPACITY("Drop Capacity"),
    SPEED("Drop Speed");

    private final String name;

    UpgradeTarget(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static UpgradeTarget fromString(String input) {
        return switch (input.toLowerCase()) {
            case "speed" -> SPEED;
            case "capacity" -> CAPACITY;
            case "drop", "drop_value", "value" -> DROP_VALUE;
            default -> throw new IllegalArgumentException("Unknown upgrade target: " + input);
        };
    }
}
