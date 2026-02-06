package com.farmforge.spawners.core;

public class Cooldown {
    private long remainingMillis;
    private long lastResumeTimeMillis;
    private boolean paused;

    public void pause(long nowMillis){

    }

    public void resume(long nowMillis){

    }

    public boolean isFinished(){
        return false;
    }

    public long getRemainingMillis(){
        return remainingMillis;
    }
}
