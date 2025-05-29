package org.example;

import Interfaces.ILandscapeElement;

public class WildGrass implements ILandscapeElement {
    @Override
    public boolean canMove() { return true; }
    @Override
    public boolean canRotate() { return true; }
    @Override
    public boolean canRemove() { return true; }
    @Override
    public void update() {} // No special behavior
}