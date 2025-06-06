package org.example;

import Interfaces.IFlammable;
import Interfaces.ILandscapeElement;

import java.util.List;

public class Tree implements ILandscapeElement {
//    private boolean burnt = false;
//
//    @Override
//    public boolean canMove() { return true; }
//    @Override
//    public boolean canRotate() { return true; }
//    @Override
//    public boolean canRemove() { return burnt; }
//
//    @Override
//    public void update() {
//    }
//
//    @Override
//    public void surroundByFire() {
//        System.out.println("Tree is now surrounded by fire!");
//        burnt = true;
//    }
//
//    @Override
//    public boolean isBurnt() {
//        return burnt;
//    }
private boolean isBurnt = false;

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public boolean canRotate() {
        return false;
    }

    @Override
    public boolean canRemove() {
        return isBurnt;
    }

    @Override
    public void update(List<ILandscapeElement> neighbors) {
        long fireCount = neighbors.stream()
                .filter(e -> e instanceof Fire)
                .count();

        if (fireCount >= 4) {
            isBurnt = true;
        }
    }

    @Override
    public void onMove() {
        // Деревья не могут двигаться
    }

    @Override
    public ILandscapeElement transform() {
        return isBurnt ? null : this; // null означает превращение в дорогу
    }

    public boolean isBurnt() {
        return isBurnt;
    }
}