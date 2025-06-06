package org.example;

import Factories.CellFactory;
import Interfaces.IFlammable;
import Interfaces.IGameFieldListener;
import Interfaces.ILandscapeElement;
import Interfaces.IWaterable;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.Collections.shuffle;

@Getter
@Setter
public class GameField {
    private int _width;
    private int _height;
    private List<List<Cell>> _cells;
    private List<LandscapeCellDecorator> landscapeDecorators = new ArrayList<>();
    private List<IGameFieldListener> _subscribers = new ArrayList<>();

    public GameField(int width, int height) {
        _width = width;
        _height = height;
    }

    public int getHeight() {
        return _height;
    }

    public int getWidth() {
        return _width;
    }

    public void loadFromLevel(String filePath) {
        List<List<Cell>> loadedCells = LevelLoader.loadFromJson(filePath);
        _height = loadedCells.size();
        _width = loadedCells.get(0).size();
        _cells = loadedCells;
        initializeLandscapeDecorators();
    }

    private void initializeLandscapeDecorators() {
        landscapeDecorators.clear();
        for (List<Cell> row : _cells) {
            for (Cell cell : row) {
                if (!cell.getIsEmpty() && cell.getLandscapeType() != null) {
                    ILandscapeElement element = createLandscapeElement(cell.getLandscapeType());
                    if (element != null) {
                        landscapeDecorators.add(new LandscapeCellDecorator(cell, element));
                    }
                }
            }
        }
    }
    /////------------------------------------------------------------------////////////////////////

    private ILandscapeElement createLandscapeElement(String landscapeType) {
        if (landscapeType == null) return null;

        switch (landscapeType.toLowerCase()) {
            case "tree": return new Tree();
            case "fire": return new Fire();
            case "flowerbed": return new FlowerBed();
            case "grass": return new WildGrass();
            case "water": return new WaterElement();
            case "burnt": return new BurntFire();
            case "grassroad": return new WildGrassRoad();
            default: return null;
        }
    }
    /////------------------------------------------------------------------////////////////////////

    public void updateLandscapeElements() {
        List<LandscapeCellDecorator> toRemove = new ArrayList<>();

        // Сначала обновляем все элементы
        for (LandscapeCellDecorator decorator : landscapeDecorators) {
            decorator.update(landscapeDecorators);
        }

        // Затем обрабатываем трансформации
        for (LandscapeCellDecorator decorator : landscapeDecorators) {
            if (decorator.landscapeElement != null &&
                    decorator.landscapeElement.shouldTransform()) {
                decorator.landscapeElement.transform(decorator.cell);
                if (decorator.landscapeElement.shouldRemoveAfterTransform()) {
                    decorator.landscapeElement = null;
                }
            }

            if (decorator.landscapeElement == null) {
                toRemove.add(decorator);
            }
        }

        landscapeDecorators.removeAll(toRemove);
    }
    public void MoveCell(Cell cell) {
        Cell emptyCell = getEmptyCell();
        if (emptyCell == null || !isAdjacent(cell, emptyCell)) return;

        /////------------------------------------------------------------------////////////////////////
        // Если перемещаем огонь, увеличиваем его счетчик
        landscapeDecorators.stream()
                .filter(d -> d.cell.equals(cell) && d.landscapeElement instanceof Fire)
                .findFirst()
                .ifPresent(decorator -> ((Fire) decorator.landscapeElement).incrementMoveCount());

        /////------------------------------------------------------------------////////////////////////

        Cell newEmptyCell = new Cell(
                cell.getPosition()[0],
                cell.getPosition()[1],
                true, false, false, null, null, null
        );

        Cell newMovedCell = new Cell(
                emptyCell.getPosition()[0],
                emptyCell.getPosition()[1],
                false,
                cell.isStart(),
                cell.isEnd(),
                cell.getDirectionEnter(),
                cell.getDirectionExit(),
                cell.getLandscapeType()
        );

        _cells.get(cell.getPosition()[1]).set(cell.getPosition()[0], newEmptyCell);
        _cells.get(emptyCell.getPosition()[1]).set(emptyCell.getPosition()[0], newMovedCell);

        updateDecoratorsAfterMove(cell, newMovedCell);
        NotifySubscribers();
    }

    private void updateDecoratorsAfterMove(Cell oldCell, Cell newCell) {
        LandscapeCellDecorator oldDecorator = landscapeDecorators.stream()
                .filter(d -> d.cell.equals(oldCell))
                .findFirst()
                .orElse(null);

        landscapeDecorators.removeIf(d -> d.cell.equals(oldCell));

        /////------------------------------------------------------------------////////////////////////
        if (newCell.getLandscapeType() != null) {
            ILandscapeElement element = null;

            if (newCell.getLandscapeType().equalsIgnoreCase("fire") &&
                    oldDecorator != null &&
                    oldDecorator.landscapeElement instanceof Fire) {
                element = ((Fire) oldDecorator.landscapeElement).copy();
            } else {
                element = createLandscapeElement(newCell.getLandscapeType());
            }

            if (element != null) {
                landscapeDecorators.add(new LandscapeCellDecorator(newCell, element));
            }
        }
        /////------------------------------------------------------------------////////////////////////
    }

    public boolean isAdjacent(Cell a, Cell b) {
        int[] posA = a.getPosition();
        int[] posB = b.getPosition();
        int dx = Math.abs(posA[0] - posB[0]);
        int dy = Math.abs(posA[1] - posB[1]);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    public Cell getEmptyCell() {
        for (List<Cell> row : _cells)
            for (Cell cell : row)
                if (cell.getIsEmpty()) return cell;
        return null;
    }

    public List<List<Cell>> getСells() {
        return this._cells;
    }

    public void AddSubscribers(IGameFieldListener subscriber) {
        _subscribers.add(subscriber);
    }

    public void RemoveSubscribers(IGameFieldListener subscriber) {
        _subscribers.remove(subscriber);
    }

    private void NotifySubscribers() {
        for (IGameFieldListener obj : _subscribers) {
            obj.CellMoved(_cells);
        }
    }
}
