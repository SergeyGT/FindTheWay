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
    public void update(Cell cell,List<LandscapeCellDecorator> allDecorators) {
        long fireCount = allDecorators.stream()
                .filter(d -> {
                    int[] pos = d.cell.getPosition();
                    int[] thisPos = cell.getPosition();
                    return Math.abs(pos[0] - thisPos[0]) <= 1 &&
                            Math.abs(pos[1] - thisPos[1]) <= 1 &&
                            d.landscapeElement instanceof Fire;
                })
                .count();

        if (fireCount >= 4 && !burnt) {
            surroundByFire();
        }
    }

    @Override
    public void surroundByFire() {
        System.out.println("Дерево окружено огнем!");
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