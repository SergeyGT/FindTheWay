package org.example;

import Interfaces.ILandscapeElement;

public class WildGrassRoad implements ILandscapeElement {
    private int maxTurnsNearRoad = 3;
    private int currentTurnsNearRoad = 0;
    private boolean isALive = true;

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public boolean canRotate() {
        return false;
    }

    @Override
    public boolean canRemove() {
        return !isALive;
    }

    @Override
    public void update() {    }

    public boolean isAlive() { return this.isALive; }

    public int getTurnsNearRoad() { return this.currentTurnsNearRoad; }

    public void incrementTurnsNearRoad() {
        if (isALive && currentTurnsNearRoad < maxTurnsNearRoad) {
            currentTurnsNearRoad++;
            if (currentTurnsNearRoad >= maxTurnsNearRoad) {
                isALive = false;
            }
        }
    }

    public void resetTurnsNearRoad() {
        currentTurnsNearRoad = 0;
    }
}