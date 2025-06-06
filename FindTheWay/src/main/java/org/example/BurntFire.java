package org.example;

import Interfaces.ILandscapeElement;

import java.util.List;

public class BurntFire implements ILandscapeElement {
    @Override public boolean canMove() { return false; }
    @Override public boolean canRotate() { return false; }
    @Override public boolean canRemove() { return false; }

    @Override
    public void update(List<ILandscapeElement> neighbors) {

    }

    @Override
    public void onMove() {

    }

    @Override
    public ILandscapeElement transform() {
        return this;
    }

   // @Override public void update() {}
}