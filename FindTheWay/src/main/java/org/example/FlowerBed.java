package org.example;

import Interfaces.ILandscapeElement;
import Interfaces.IWaterSource;
import Interfaces.IWaterable;

import java.util.Arrays;
import java.util.List;

public class FlowerBed implements ILandscapeElement, IWaterable {
    private int turnsWithoutWater = 0;
    private boolean isAlive = true;
    private final int maxTurnsWithoutWater = 3;
    private boolean wasWateredThisTurn = false;

    @Override
    public boolean canMove(Cell cell) { return false; }
    @Override
    public boolean canRotate(Cell cell) { return false; }
    @Override
    public boolean canRemove(Cell cell) { return !isAlive; }

    @Override
    public void update(Cell cell,List<LandscapeCellDecorator> allDecorators) {
        checkWatering(allDecorators, cell);

        if (!wasWateredThisTurn) {
            turnsWithoutWater++;
            System.out.println("FlowerBed turns without water: " + turnsWithoutWater +
                    "/" + maxTurnsWithoutWater);

            if (turnsWithoutWater > maxTurnsWithoutWater) {
                isAlive = false;
                System.out.println("FlowerBed has died and will turn into wild grass!");
            }
        } else {
            System.out.println("FlowerBed is properly watered");
            turnsWithoutWater = 0; // Сбрасываем счетчик при поливе
        }
        wasWateredThisTurn = false;
    }

    @Override
    public boolean shouldTransform() {
        return !isAlive;
    }

    @Override
    public void transform(Cell cell) {
        System.out.println("Transforming dead FlowerBed to WildGrass at " +
                Arrays.toString(cell.getPosition()));
        cell.setLandscapeType("grass");
    }

    @Override public void onMove() {}

    @Override
    public boolean shouldRemoveAfterTransform() {
        return false;
    }

    @Override
    public void checkWatering(List<LandscapeCellDecorator> allDecorators, Cell cell) {
        boolean hasWaterSource = allDecorators.stream()
                .anyMatch(d -> {
                    int[] pos = d.cell.getPosition();
                    int[] thisPos = cell.getPosition();
                    return Math.abs(pos[0] - thisPos[0]) <= 1 &&
                            Math.abs(pos[1] - thisPos[1]) <= 1 &&
                            d.landscapeElement instanceof IWaterSource;
                });

        if (hasWaterSource) {
            water();
        }
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

    @Override public ILandscapeElement copyState() {
        return null;
    }

    public int getTurnsWithoutWater() {
        return turnsWithoutWater;
    }
}