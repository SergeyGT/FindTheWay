package org.example;

import Interfaces.ILandscapeElement;

public class Fire implements ILandscapeElement {
    private int moveCount = 0;
    private final int maxMoves = 3;

    @Override
    public boolean canMove() { return moveCount < maxMoves; }
    @Override
    public boolean canRotate() { return true; }
    @Override
    public boolean canRemove() { return moveCount >= maxMoves; }

    @Override
    public void update() {} // No special behavior

    public void incrementMoveCount() { moveCount++; }
}