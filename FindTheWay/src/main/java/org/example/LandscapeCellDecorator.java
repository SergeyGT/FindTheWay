package org.example;

import Interfaces.ILandscapeElement;
import Interfaces.IWaterable;

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

    public void update() {
        if (landscapeElement == null) return;

        landscapeElement.update();

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
        else if (landscapeElement instanceof FlowerBed && !((FlowerBed) landscapeElement).isAlive()) {
            this.landscapeElement = new WildGrass();
        }
    }


}
