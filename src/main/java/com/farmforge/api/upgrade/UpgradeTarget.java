package com.farmforge.api.upgrade;

public enum UpgradeTarget {
    DROP_VALUE("Drop Value", 5),
    CAPACITY("Drop Capacity", 5),
    SPEED("Drop Speed", 3);

    private final String name;
    private final int max;

    UpgradeTarget(String name, int max){
        this.name = name;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public int getMax() {
        return max;
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
