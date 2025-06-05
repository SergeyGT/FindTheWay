package org.example;

import Interfaces.IFlammable;
import Interfaces.ILandscapeElement;

public class Tree implements ILandscapeElement, IFlammable {
    private boolean burnt = false;

    @Override
    public boolean canMove() { return true; }
    @Override
    public boolean canRotate() { return true; }
    @Override
    public boolean canRemove() { return burnt; }

    @Override
    public void update() {
    }

    @Override
    public void surroundByFire() {
        System.out.println("Tree is now surrounded by fire!");
        burnt = true;
    }

    @Override
    public boolean isBurnt() {
        return burnt;
    }
}