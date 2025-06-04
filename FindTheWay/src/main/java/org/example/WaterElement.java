package org.example;

import Interfaces.ILandscapeElement;
import Interfaces.IWaterSource;

public class WaterElement implements ILandscapeElement, IWaterSource {
    @Override
    public boolean canMove() { return true; }
    @Override
    public boolean canRotate() { return false; }
    @Override
    public boolean canRemove() { return false; }
    @Override
    public void update() {}
}
