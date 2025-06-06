package org.example;

import Interfaces.ILandscapeElement;
import Interfaces.IWaterable;

import java.util.List;

//
//public class FlowerBed implements ILandscapeElement, IWaterable {
//    private int turnsWithoutWater = 0;
//    private boolean isAlive = true;
//    private final int maxTurnsWithoutWater = 3;
//    private boolean wasWateredThisTurn = false;
//
//    @Override
//    public boolean canMove() { return false; }
//    @Override
//    public boolean canRotate() { return false; }
//    @Override
//    public boolean canRemove() { return !isAlive; }
//
//    @Override
//    public void update() {
//        if (!wasWateredThisTurn) {
//            turnsWithoutWater++;
//            System.out.println("FlowerBed turns without water: " + turnsWithoutWater +
//                    "/" + maxTurnsWithoutWater);
//
//            if (turnsWithoutWater >= maxTurnsWithoutWater) {
//                isAlive = false;
//                System.out.println("FlowerBed has died and will turn into wild grass!");
//            }
//        } else {
//            System.out.println("FlowerBed is properly watered");
//        }
//        wasWateredThisTurn = false;
//    }
//
//    @Override
//    public void water() {
//        turnsWithoutWater = 0;
//        wasWateredThisTurn = true;
//        System.out.println("FlowerBed has been watered");
//    }
//
//    @Override
//    public boolean isWatered() {
//        return wasWateredThisTurn;
//    }
//
//    public boolean isAlive() {
//        return isAlive;
//    }
//
//    public int getTurnsWithoutWater() {
//        return turnsWithoutWater;
//    }
//}
public class FlowerBed implements ILandscapeElement, IWaterable {
    private int turnsWithoutWater = 0;
    private boolean isAlive = true;
    private final int maxTurnsWithoutWater = 3;
    private boolean wasWateredThisTurn = false;

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
        return !isAlive;
    }

    @Override
    public void update(List<ILandscapeElement> neighbors) {
        if (!wasWateredThisTurn) {
            turnsWithoutWater++;
            if (turnsWithoutWater >= maxTurnsWithoutWater) {
                isAlive = false;
            }
        }
        wasWateredThisTurn = false;
    }

    @Override
    public void water() {
        turnsWithoutWater = 0;
        wasWateredThisTurn = true;
    }

    @Override
    public boolean isWatered() {
        return wasWateredThisTurn;
    }

    @Override
    public void onMove() {
        // Клумбы не могут двигаться
    }

    @Override
    public ILandscapeElement transform() {
        return !isAlive ? new WildGrass() : this;
    }

    public boolean isAlive() {
        return isAlive;
    }
}