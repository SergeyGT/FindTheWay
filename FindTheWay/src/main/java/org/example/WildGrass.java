package org.example;

import Interfaces.ILandscapeElement;

import java.util.List;

public class WildGrass implements ILandscapeElement {
    @Override
    public boolean canMove() { return true; }
    @Override
    public boolean canRotate() { return true; }
    @Override
    public boolean canRemove() { return true; }

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

    @Override
    public String getLandscapeType() {
        return "grass";
    }
}