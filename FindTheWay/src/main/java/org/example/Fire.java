package org.example;

import Interfaces.ILandscapeElement;

import java.util.List;

public class Fire implements ILandscapeElement {
    private int moveCount = 0;
    private int maxMoves = 3;

    public Fire() { this(0); }
    private Fire(int currentMoves) {
        this.moveCount = currentMoves;
        this.maxMoves = 3;
    }

    public Fire copy() { return new Fire(this.moveCount); }

    @Override
    public boolean canMove(Cell cell) { return moveCount < maxMoves; }
    @Override
    public boolean canRotate(Cell cell) { return true; }
    @Override
    public boolean canRemove(Cell cell) { return moveCount >= maxMoves; }
    @Override
    public void update(Cell cell) {}
    @Override
    public boolean shouldTransform() { return moveCount >= maxMoves; }
    @Override
    public void transform(Cell cell) {
        cell.setLandscapeType("burnt");
        cell.setIsEmpty(false);
    }
    @Override
    public boolean shouldRemoveAfterTransform() { return false; }
    @Override
    public void checkWatering(List<LandscapeCellDecorator> allDecorators, Cell cell) {}

    public void incrementMoveCount() {
        if (moveCount < maxMoves) {
            moveCount++;
            System.out.println("Fire moved: " + moveCount + "/" + maxMoves);
        }
    }

    public int getMoveCount() { return moveCount; }
}