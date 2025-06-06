package org.example;

import Interfaces.ILandscapeElement;

import java.util.List;

public class WildGrassRoad implements ILandscapeElement {
    private int maxTurnsNearRoad = 3;
    private int currentTurnsNearRoad = 0;
    private boolean isALive = true;

    @Override
    public boolean canMove(Cell cell) { return true; }
    @Override
    public boolean canRotate(Cell cell) { return false; }
    @Override
    public boolean canRemove(Cell cell) { return !isALive; }

    @Override
    public void update(Cell cell) {}

    @Override
    public boolean shouldTransform() {
        return !isALive;
    }

    @Override
    public void transform(Cell cell) {
        Direction enterDir = null;
        Direction exitDir = null;

        // Логика определения направления из соседей должна быть в GameField
        // Здесь просто пример
        if (enterDir == null) enterDir = new Direction(DirectionEnum.LEFT);
        if (exitDir == null) exitDir = new Direction(DirectionEnum.RIGHT);

        cell.set_directionEnter(enterDir);
        cell.set_directionExit(exitDir);
        cell.setLandscapeType(null);
        cell.setIsEmpty(false);
    }

    @Override
    public boolean shouldRemoveAfterTransform() {
        return true;
    }

    @Override
    public void checkWatering(List<LandscapeCellDecorator> allDecorators, Cell cell) {}

    public boolean isAlive() { return this.isALive; }
    public int getTurnsNearRoad() { return this.currentTurnsNearRoad; }

    public void incrementTurnsNearRoad() {
        if (isALive && currentTurnsNearRoad < maxTurnsNearRoad) {
            currentTurnsNearRoad++;
            if (currentTurnsNearRoad >= maxTurnsNearRoad) {
                isALive = false;
            }
        }
    }

    public void resetTurnsNearRoad() {
        currentTurnsNearRoad = 0;
    }
}