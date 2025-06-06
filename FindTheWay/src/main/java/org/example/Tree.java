package org.example;

import Interfaces.IFlammable;
import Interfaces.ILandscapeElement;

import java.util.Arrays;
import java.util.List;

public class Tree implements ILandscapeElement, IFlammable {
    private boolean burnt = false;

    @Override
    public boolean canMove(Cell cell) { return true; }
    @Override
    public boolean canRotate(Cell cell) { return true; }
    @Override
    public boolean canRemove(Cell cell) { return burnt; }

    @Override
    public void update(Cell cell) {}

    @Override
    public void surroundByFire() {
        System.out.println("Tree is now surrounded by fire!");
        burnt = true;
    }

    @Override
    public boolean isBurnt() {
        return burnt;
    }

    @Override
    public boolean shouldTransform() {
        return burnt;
    }

    @Override
    public void transform(Cell cell) {
        System.out.println("Transforming burnt tree to road at " +
                Arrays.toString(cell.getPosition()));
        cell.set_directionEnter(new Direction(DirectionEnum.LEFT));
        cell.set_directionExit(new Direction(DirectionEnum.RIGHT));
        cell.setLandscapeType(null);
        cell.setIsEmpty(false);
        cell.setStart(false);
        cell.setEnd(false);
    }

    @Override
    public boolean shouldRemoveAfterTransform() {
        return true;
    }

    @Override
    public void checkWatering(List<LandscapeCellDecorator> allDecorators, Cell cell) {}
}