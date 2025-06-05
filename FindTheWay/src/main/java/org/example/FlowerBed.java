package org.example;

import Interfaces.ILandscapeElement;
import Interfaces.IWaterable;

public class FlowerBed implements ILandscapeElement, IWaterable {
    private int turnsWithoutWater = 0;
    private boolean isAlive = true;
    private final int maxTurnsWithoutWater = 3;
    private boolean wasWateredThisTurn = false;

    @Override
    public boolean canMove() { return false; } // Нельзя перемещать
    @Override
    public boolean canRotate() { return false; } // Нельзя поворачивать
    @Override
    public boolean canRemove() { return !isAlive; } // Можно удалить только мертвую

    @Override
    public void update() {
        if (!wasWateredThisTurn) {
            turnsWithoutWater++;
            System.out.println("FlowerBed turns without water: " + turnsWithoutWater +
                    "/" + maxTurnsWithoutWater);

            if (turnsWithoutWater >= maxTurnsWithoutWater) {
                isAlive = false;
                System.out.println("FlowerBed has died and will turn into wild grass!");
            }
        } else {
            System.out.println("FlowerBed is properly watered");
        }
        wasWateredThisTurn = false; // Сбрасываем флаг после обработки
    }

    @Override
    public void water() {
        turnsWithoutWater = 0;
        wasWateredThisTurn = true;
        System.out.println("FlowerBed has been watered");
    }

    @Override
    public boolean isWatered() {
        return wasWateredThisTurn;
    }

    public boolean isAlive() {
        return isAlive;
    }
}