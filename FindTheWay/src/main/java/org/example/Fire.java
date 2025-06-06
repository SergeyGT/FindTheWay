package org.example;

import Interfaces.ILandscapeElement;

import java.util.List;

public class Fire implements ILandscapeElement {
    private int moveCount = 0;
    private final int maxMoves = 3;

    @Override
    public boolean canMove() {
        return moveCount < maxMoves;
    }

    @Override
    public boolean canRotate() {
        return true;
    }

    @Override
    public boolean canRemove() {
        return moveCount >= maxMoves;
    }

    @Override
    public void update(List<ILandscapeElement> neighbors) {
        // Огонь не зависит от соседей
    }

    @Override
    public void onMove() {
        if (moveCount < maxMoves) {
            moveCount++;
        }
    }
    public Fire copy() {
       return new Fire(this.moveCount);
   }

    @Override
    public ILandscapeElement transform() {
        return canRemove() ? new BurntFire() : this;
    }
//    private int moveCount = 0;
//    private int maxMoves = 3;
//
//    public Fire() {
//        this(0);
//    }
//
//    // Конструктор для копирования
//    private Fire(int currentMoves) {
//        this.moveCount = currentMoves;
//        this.maxMoves = 3;
//    }
//
//    // Метод для клонирования
//    public Fire copy() {
//        return new Fire(this.moveCount);
//    }
//
//    @Override
//    public boolean canMove() {
//        return moveCount < maxMoves;
//    }
//
//    @Override
//    public boolean canRotate() {
//        return true;
//    }
//
//    @Override
//    public boolean canRemove() {
//        return moveCount >= maxMoves;
//    }
//
//    @Override
//    public void update() {}
//
//    public void incrementMoveCount() {
//        if (moveCount < maxMoves) {
//            moveCount++;
//            System.out.println("Fire moved: " + moveCount + "/" + maxMoves);
//        }
//    }
//
//    public int getMoveCount() {
//        return moveCount;
//    }
}