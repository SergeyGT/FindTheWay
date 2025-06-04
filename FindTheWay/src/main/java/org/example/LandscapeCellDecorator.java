package org.example;

import Interfaces.ILandscapeElement;
import Interfaces.IWaterSource;
import Interfaces.IWaterable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LandscapeCellDecorator {
    public final Cell cell;
    public ILandscapeElement landscapeElement;

    public LandscapeCellDecorator(Cell cell, ILandscapeElement element) {
        this.cell = cell;
        this.landscapeElement = element;
    }

    public boolean canMove() {
        if (landscapeElement == null) return true;
        return landscapeElement.canMove() && !cell.getIsEmpty();
    }

    public boolean canRotate() {
        return landscapeElement.canRotate() && !cell.getIsEmpty();
    }

    public boolean canRemove() {
        return landscapeElement.canRemove() && !cell.getIsEmpty();
    }

    private void checkWatering(List<LandscapeCellDecorator> allDecorators) {
        if (landscapeElement instanceof IWaterable) {
            boolean hasWaterSource = getNeighbors(allDecorators).stream()
                    .anyMatch(n -> n.landscapeElement instanceof IWaterSource);

            if (hasWaterSource) {
                ((IWaterable) landscapeElement).water();
            }
        }
    }

    private List<LandscapeCellDecorator> getNeighbors(List<LandscapeCellDecorator> allDecorators) {
        List<LandscapeCellDecorator> neighbors = new ArrayList<>();
        int[] pos = this.cell.getPosition();

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int[] dir : directions) {
            int nx = pos[0] + dir[0];
            int ny = pos[1] + dir[1];

            allDecorators.stream()
                    .filter(d -> {
                        int[] neighborPos = d.cell.getPosition();
                        return neighborPos[0] == nx && neighborPos[1] == ny;
                    })
                    .findFirst()
                    .ifPresent(neighbors::add);
        }

        return neighbors;
    }

    public void update(List<LandscapeCellDecorator> allDecorators) {
        if (landscapeElement == null) return;

        if (landscapeElement instanceof FlowerBed) {
            checkWatering(allDecorators);
        }


        landscapeElement.update();

        if (landscapeElement instanceof FlowerBed && !((FlowerBed) landscapeElement).isAlive()) {
            System.out.println("Transforming dead FlowerBed to WildGrass at " +
                    Arrays.toString(cell.getPosition()));
            // Обновляем тип ландшафта клетки
            cell.setLandscapeType("grass");
            // Заменяем элемент на дикорастущую траву
            this.landscapeElement = new WildGrass();
        }

        if (landscapeElement instanceof Tree && ((Tree) landscapeElement).isBurnt()) {
            System.out.println("Transforming burnt tree to road at " +
                    Arrays.toString(cell.getPosition()));

            // Превращаем в горизонтальную дорогу
            cell.set_directionEnter(new Direction(DirectionEnum.LEFT));
            cell.set_directionExit(new Direction(DirectionEnum.RIGHT));
            cell.setLandscapeType(null);
            cell.setIsEmpty(false); // Дорога - не пустая клетка
            cell.setStart(false);
            cell.setEnd(false);

            this.landscapeElement = null;
        }
//        else if (landscapeElement instanceof FlowerBed && !((FlowerBed) landscapeElement).isAlive()) {
//            System.out.println("Transforming dead FlowerBed to WildGrass at " +
//                    Arrays.toString(cell.getPosition()));
//            this.landscapeElement = new WildGrass();
//        }
        //landscapeElement.update();
    }

    // Дополнительные методы для работы с конкретными типами элементов
    public void waterIfPossible() {
        if (landscapeElement instanceof IWaterable) {
            ((IWaterable) landscapeElement).water();
        }
    }

    public void checkFireSurrounding(List<LandscapeCellDecorator> neighbors) {
        if (landscapeElement instanceof Tree) {
            int fireCount = (int) neighbors.stream()
                    .filter(n -> n.landscapeElement instanceof Fire)
                    .count();

            // Дебаг-логирование
            System.out.println("Fire neighbors for Tree: " + fireCount);

            if (fireCount >= 4) {
                ((Tree) landscapeElement).surroundByFire();
            }
        }
    }
}
