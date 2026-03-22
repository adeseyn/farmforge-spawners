package com.farmforge.spawners.core;

import java.util.Objects;

public class SpawnerPosition {

    private final String world;
    private final int x;
    private final int y;
    private final int z;

    public SpawnerPosition(String world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getWorld() { return world; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpawnerPosition that)) return false;
        return x == that.x
                && y == that.y
                && z == that.z
                && Objects.equals(world, that.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, x, y, z);
    }

    @Override
    public String toString() {
        return world + ":" + x + "," + y + "," + z;
    }
}
