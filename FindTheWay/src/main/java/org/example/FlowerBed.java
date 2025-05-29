package org.example;

import Interfaces.ILandscapeElement;
import Interfaces.IWaterable;

public class FlowerBed implements ILandscapeElement, IWaterable {
    private int turnsWithoutWater = 0;
    private boolean isAlive = true;
    private final int maxTurnsWithoutWater = 3;

    @Override
    public boolean canMove() { return false; } // Нельзя перемещать
    @Override
    public boolean canRotate() { return false; } // Нельзя поворачивать
    @Override
    public boolean canRemove() { return !isAlive; } // Можно удалить только мертвую

    @Override
    public void update() {
        if (!isWatered()) {
            turnsWithoutWater++;
            System.out.println("FlowerBed turns without water: " + turnsWithoutWater);

            if (turnsWithoutWater >= maxTurnsWithoutWater) {
                isAlive = false;
                System.out.println("FlowerBed has died!");
            }
        } else {
            turnsWithoutWater = 0; // Сбрасываем счетчик при поливе
        }
    }

    @Override
    public void water() {
        turnsWithoutWater = 0;
        System.out.println("FlowerBed has been watered");
    }

    @Override
    public boolean isWatered() {
        return turnsWithoutWater == 0;
    }

    public boolean isAlive() {
        return isAlive;
    }
}