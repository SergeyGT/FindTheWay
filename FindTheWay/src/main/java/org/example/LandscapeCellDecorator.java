package org.example;

import Interfaces.ILandscapeElement;
import Interfaces.IWaterable;

import java.util.List;

public class LandscapeCellDecorator {
    private final Cell cell;
    private ILandscapeElement landscapeElement;

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

    public boolean canRemove() {
        return landscapeElement.canRemove() && !cell.getIsEmpty();
    }

    public void update() {
        landscapeElement.update();

        if (landscapeElement instanceof Tree && ((Tree) landscapeElement).isBurnt()) {
            // Превращаем дерево в ячейку с направлением "прямая дорога" (например, слева направо)
            cell.set_directionEnter(new Direction(DirectionEnum.LEFT));
            cell.set_directionExit(new Direction(DirectionEnum.RIGHT));
            cell.setStart(false);
            cell.setEnd(false);

            // Удаляем ландшафтный элемент, так как теперь это обычная ячейка
            this.landscapeElement = null;
            GameField.getInstance().removeLandscapeDecorator(this);
        }
        else if (landscapeElement instanceof FlowerBed && !((FlowerBed) landscapeElement).isAlive()) {
            this.landscapeElement = new WildGrass();
        }
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
            ((Tree) landscapeElement).surroundByFire();
        }
    }
}
