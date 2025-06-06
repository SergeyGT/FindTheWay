package org.example;

import Interfaces.ILandscapeElement;
import Interfaces.IWaterable;
import org.example.*;

import java.util.ArrayList;
import java.util.List;

public class LandscapeCellDecorator {
    public final Cell cell;
    public ILandscapeElement landscapeElement;

    public LandscapeCellDecorator(Cell cell, ILandscapeElement element) {
        this.cell = cell;
        this.landscapeElement = element;
    }

    public boolean canMove() {
        return landscapeElement.canMove() && !cell.getIsEmpty();
    }

    public boolean canRotate() {
        return landscapeElement.canRotate() && !cell.getIsEmpty();
    }

    public void handleMove() {
        landscapeElement.onMove();
    }

    public void update(List<LandscapeCellDecorator> allDecorators) {
        // Получаем соседей
        List<ILandscapeElement> neighbors = getNeighbors(allDecorators);

        // Обновляем элемент
        landscapeElement.update(neighbors);

        // Проверяем полив для водозависимых элементов
        checkWatering(allDecorators);

        // Проверяем трансформацию
        ILandscapeElement transformed = landscapeElement.transform();
        if (transformed != landscapeElement) {
            if (transformed == null) {
                transformToRoad(); // ← Добавляем вызов
            } else {
                landscapeElement = transformed;
                updateCellType();
            }
        }
    }

    private void transformToRoad() {
        cell.set_directionEnter(new Direction(DirectionEnum.LEFT));
        cell.set_directionExit(new Direction(DirectionEnum.RIGHT));
        cell.setLandscapeType(null);
        cell.setIsEmpty(false);
        cell.setStart(false);
        cell.setEnd(false);
        landscapeElement = null;
    }

    private void updateCellType() {
        if (landscapeElement != null) {
            cell.setLandscapeType(landscapeElement.getLandscapeType());
        }
    }

    private void checkWatering(List<LandscapeCellDecorator> allDecorators) {
        if (landscapeElement instanceof IWaterable) {
            boolean hasWaterSource = getNeighbors(allDecorators).stream()
                    .anyMatch(n -> n instanceof WaterElement);

            if (hasWaterSource) {
                ((IWaterable) landscapeElement).water();
            }
        }
    }

    private List<ILandscapeElement> getNeighbors(List<LandscapeCellDecorator> allDecorators) {
        List<ILandscapeElement> neighbors = new ArrayList<>();
        int[] pos = cell.getPosition();

        int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        for (int[] dir : directions) {
            int nx = pos[0] + dir[0];
            int ny = pos[1] + dir[1];

            // Ищем декоратор, который находится в соседней позиции
            LandscapeCellDecorator neighborDecorator = allDecorators.stream()
                    .filter(d -> {
                        int[] neighborPos = d.cell.getPosition();
                        return neighborPos[0] == nx && neighborPos[1] == ny;
                    })
                    .findFirst()
                    .orElse(null);

            if (neighborDecorator != null) {
                // Добавляем элемент соседа (не null)
                neighbors.add(neighborDecorator.landscapeElement);
            } else {
                // Если декоратора нет — значит там дорога (null)
                neighbors.add(null);
            }
        }

        return neighbors;
    }
}