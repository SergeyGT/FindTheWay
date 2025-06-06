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
        if (moveCount < maxMoves) {
            moveCount++;
        }
        System.out.println("Огонь обновлён: " + moveCount + "/" + maxMoves);
    }

    @Override
    public void onMove() {
        // можно оставить пустым или логировать факт перемещения
        System.out.println("Fire was moved");
    }
    // Конструктор по умолчанию
    public Fire() {
    }

    // Конструктор для копирования с сохранением moveCount
    public Fire(int moveCount) {
        this.moveCount = moveCount;
    }

    public Fire copy() {
        return new Fire(this.moveCount);
    }

    @Override
    public ILandscapeElement transform() {
        return canRemove() ? new BurntFire() : this;
    }
}