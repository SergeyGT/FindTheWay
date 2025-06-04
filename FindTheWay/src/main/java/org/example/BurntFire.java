package org.example;

import Interfaces.ILandscapeElement;

public class BurntFire implements ILandscapeElement {
    @Override public boolean canMove() { return false; }
    @Override public boolean canRotate() { return false; }
    @Override public boolean canRemove() { return false; }
    @Override public void update() {}
}