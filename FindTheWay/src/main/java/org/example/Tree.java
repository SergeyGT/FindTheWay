package org.example;

import Interfaces.IFlammable;
import Interfaces.ILandscapeElement;

public class Tree implements ILandscapeElement, IFlammable {
    private int fireSurrounding = 0;
    private boolean isAlive = true;

    @Override
    public boolean canMove() { return true; }
    @Override
    public boolean canRotate() { return true; }
    @Override
    public boolean canRemove() { return !isAlive; }

    @Override
    public void update() {
        if (fireSurrounding >= 4) {
            isAlive = false;
            // Превращается в дорогу (логика преобразования будет в декораторе)
        }
    }

    @Override
    public void surroundByFire() { fireSurrounding++; }
    @Override
    public boolean isBurnt() { return !isAlive; }
}
