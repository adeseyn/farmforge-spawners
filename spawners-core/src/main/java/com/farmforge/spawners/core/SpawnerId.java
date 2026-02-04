package com.farmforge.spawners.core;

import java.util.UUID;

public class SpawnerId {

    private final UUID value;

    public SpawnerId(UUID value){
        if(value == null) {
            throw new IllegalArgumentException("SpawnerId value cannot be null");
        }
        this.value = value;
    }

    public UUID getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
