package org.example;

import Interfaces.IFlammable;
import Interfaces.ILandscapeElement;

import java.util.List;

public class Tree implements ILandscapeElement {
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

        System.out.println(fireCount);

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

    @Override
    public String getLandscapeType() {
        return "tree";
    }
}