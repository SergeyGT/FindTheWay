package org.example;

import Factories.LandscapeFactory;
import Interfaces.ILandscapeElement;

import java.util.ArrayList;
import java.util.List;

public class LandscapeManager {
    private final List<LandscapeCellDecorator> decorators = new ArrayList<>();

    public void initialize(List<List<Cell>> cells) {
        decorators.clear();
        for (List<Cell> row : cells) {
            for (Cell cell : row) {
                if (!cell.getIsEmpty() && cell.getLandscapeType() != null) {
                    ILandscapeElement element = LandscapeFactory.create(cell.getLandscapeType());
                    if (element != null) {
                        decorators.add(new LandscapeCellDecorator(cell, element));
                    }
                }
            }
        }
    }

    public void update() {
        List<LandscapeCellDecorator> toRemove = new ArrayList<>();
        for (LandscapeCellDecorator decorator : decorators) {
            decorator.update(decorators);
            if (decorator.landscapeElement == null || decorator.landscapeElement.canRemove()) {
                toRemove.add(decorator);
            }
        }
        decorators.removeAll(toRemove);
    }

    public void handleMove(Cell cell, Cell target) {
        LandscapeCellDecorator decorator = getDecorator(cell);
        if (decorator != null) {
            decorator.handleMove();
        }
        updateAfterMove(cell, target, decorator);
    }

    public boolean canMove(Cell cell) {
        LandscapeCellDecorator decorator = getDecorator(cell);
        if (decorator == null) return true;

        if (decorator.landscapeElement instanceof FlowerBed) {
            return false;
        }
        return decorator.canMove();
    }

    public LandscapeCellDecorator getDecorator(Cell cell) {
        return decorators.stream()
                .filter(d -> d.cell.equals(cell))
                .findFirst()
                .orElse(null);
    }

    public void updateAfterMove(Cell from, Cell to, LandscapeCellDecorator oldDecorator) {
        decorators.removeIf(d -> d.cell.equals(from));

        if (to.getLandscapeType() != null) {
            ILandscapeElement newElement = null;

            // Особый случай для огня - сохраняем его состояние при перемещении
            if (to.getLandscapeType().equalsIgnoreCase("fire")) {
                if (oldDecorator != null && oldDecorator.landscapeElement instanceof Fire) {
                    // Копируем текущее состояние огня
                    newElement = ((Fire) oldDecorator.landscapeElement).copy();
                } else {
                    // Создаем новый огонь с начальным состоянием
                    newElement = LandscapeFactory.create(to.getLandscapeType());
                }
            } else {
                // Для других элементов просто создаем новый
                newElement = LandscapeFactory.create(to.getLandscapeType());
            }

            if (newElement != null) {
                decorators.add(new LandscapeCellDecorator(to, newElement));
            }
        }
    }

    public List<LandscapeCellDecorator> getDecorators() {
        return decorators;
    }
}
