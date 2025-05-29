package org.example;

import Interfaces.ILandscapeElement;
import Interfaces.IWaterable;

public class FlowerBed implements ILandscapeElement, IWaterable {
    private int turnsWithoutWater = 0;
    private boolean isAlive = true;

    @Override
    public boolean canMove() { return false; }
    @Override
    public boolean canRotate() { return false; }
    @Override
    public boolean canRemove() { return !isAlive; }

    @Override
    public void update() {
        if (!isWatered()) {
            turnsWithoutWater++;
            if (turnsWithoutWater >= 3) {
                isAlive = false;
            }
        } else {
            turnsWithoutWater = 0;
        }
    }

    @Override
    public void water() { turnsWithoutWater = 0; }
    @Override
    public boolean isWatered() { return turnsWithoutWater == 0; }
}

