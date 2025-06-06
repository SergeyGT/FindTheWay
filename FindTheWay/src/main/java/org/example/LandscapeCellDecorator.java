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
        return landscapeElement.canMove(cell);
    }

    public boolean canRotate() {
        if (landscapeElement == null) return true;
        return landscapeElement.canRotate(cell);
    }

    public boolean canRemove() {
        if (landscapeElement == null) return true;
        return landscapeElement.canRemove(cell);
    }

    public void update(List<LandscapeCellDecorator> allDecorators) {
        if (landscapeElement == null) return;

        //landscapeElement.checkWatering(allDecorators, cell);
        //landscapeElement.update(cell);
        // Вызываем update с передачей всех декораторов
        landscapeElement.update(cell, allDecorators);

        if (landscapeElement.shouldTransform()) {
            landscapeElement.transform(cell);
            if (landscapeElement.shouldRemoveAfterTransform()) {
                landscapeElement = null;
            }
        }
    }

    public List<LandscapeCellDecorator> getNeighbors(List<LandscapeCellDecorator> allDecorators) {
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
}
