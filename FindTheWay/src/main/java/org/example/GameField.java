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

    public GameField(int width, int height){
        _width = width;
        _height = height;
    }


    public int getHeight(){
        return _height;
    }

    public int getWidth(){
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
                // Проверяем, что клетка не пустая и у нее есть тип ландшафта
                if (!cell.getIsEmpty() && cell.getLandscapeType() != null) {
                    ILandscapeElement element = createLandscapeElement(cell.getLandscapeType());
                    if (element != null) {
                        landscapeDecorators.add(new LandscapeCellDecorator(cell, element));
                    }
                }
            }
        }
    }

    private ILandscapeElement createLandscapeElement(String landscapeType) {
        if (landscapeType == null) {
            return null; // Не создаем элемент, если тип ландшафта не указан
        }

        switch (landscapeType.toLowerCase()) {
            case "tree":
                return new Tree();
            case "fire":
                return new Fire();
            case "flowerbed":
                return new FlowerBed();
            case "grass":
                return new WildGrass();
            case "water":
                return new WaterElement();
            case "burnt":
                return new BurntFire();
            default:
                return null; // Игнорируем неизвестные типы
        }
    }

    public void updateLandscapeElements() {
        List<LandscapeCellDecorator> toRemove = new ArrayList<>();


        for (LandscapeCellDecorator decorator : landscapeDecorators) {
            if (decorator.landscapeElement instanceof Tree) {
                List<LandscapeCellDecorator> neighbors = getNeighbors(decorator);
                int fireCount = (int) neighbors.stream()
                        .filter(n -> n.landscapeElement instanceof Fire)
                        .count();

                if (fireCount >= 4) {
                    ((Tree) decorator.landscapeElement).surroundByFire();
                    System.out.println("Tree at " + Arrays.toString(decorator.cell.getPosition()) +
                            " surrounded by " + fireCount + " fires");
                }
            }
        }

        for (LandscapeCellDecorator decorator : landscapeDecorators) {
            decorator.update(landscapeDecorators);
            if (decorator.landscapeElement == null) {
                toRemove.add(decorator);
                continue;
            }


            if (decorator.landscapeElement instanceof Fire && decorator.landscapeElement.canRemove()) {
                decorator.cell.setLandscapeType("BURNT");
                decorator.landscapeElement = new BurntFire();
                decorator.cell.setIsEmpty(false);
            }
        }

        landscapeDecorators.removeAll(toRemove);
    }

    public boolean canMoveCell(Cell cell) {
        if (cell.getIsEmpty()) return false;

        // Проверяем декоратор
        return landscapeDecorators.stream()
                .filter(d -> d.cell.equals(cell))
                .findFirst()
                .map(decorator -> {
                    if (decorator.landscapeElement instanceof FlowerBed) {
                        return false; // Клумбы нельзя перемещать никогда
                    }
                    // Специальная проверка для огня
                    if (decorator.landscapeElement instanceof Fire) {
                        return ((Fire) decorator.landscapeElement).canMove();
                    }
                    return decorator.canMove();
                })
                .orElse(true); // Клетки без декоратора можно перемещать
    }

    private List<LandscapeCellDecorator> getNeighbors(LandscapeCellDecorator decorator) {
        List<LandscapeCellDecorator> neighbors = new ArrayList<>();
        int[] pos = decorator.cell.getPosition();

        // Проверяем все 4 возможных направления
        int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        for (int[] dir : directions) {
            int nx = pos[0] + dir[0];
            int ny = pos[1] + dir[1];

            if (nx >= 0 && nx < _width && ny >= 0 && ny < _height) {
                Cell neighborCell = _cells.get(ny).get(nx);
                landscapeDecorators.stream()
                        .filter(d -> d.cell.equals(neighborCell))
                        .findFirst()
                        .ifPresent(neighbors::add);
            }
        }

        return neighbors;
    }


    public void MoveCell(Cell cell){
        Cell emptyCell = getEmptyCell();
        if (emptyCell == null || !isAdjacent(cell, emptyCell)) return;

        System.out.println("До перемещения:");
        System.out.println("Кликнутая: " + cell + " на " + Arrays.toString(cell.getPosition()));
        System.out.println("Пустая: " + emptyCell + " на " + Arrays.toString(emptyCell.getPosition()));

        // Проверяем, перемещаем ли мы огонь
        boolean isMovingFire = cell.getLandscapeType() != null &&
                cell.getLandscapeType().equalsIgnoreCase("fire");

        // Если перемещаем огонь, увеличиваем его счетчик
        if (isMovingFire) {
            landscapeDecorators.stream()
                    .filter(d -> d.cell.equals(cell) && d.landscapeElement instanceof Fire)
                    .findFirst()
                    .ifPresent(decorator -> {
                        ((Fire) decorator.landscapeElement).incrementMoveCount();
                    });
        }

        Cell newEmptyCell = new Cell(
                cell.getPosition()[0],
                cell.getPosition()[1],
                true, false, false, null, null,null
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

        // Заменяем клетки в сетке
        _cells.get(cell.getPosition()[1]).set(cell.getPosition()[0], newEmptyCell);
        _cells.get(emptyCell.getPosition()[1]).set(emptyCell.getPosition()[0], newMovedCell);

        System.out.println("После перемещения:");
        System.out.println("Новая пустая: " + newEmptyCell + " на " +
                Arrays.toString(newEmptyCell.getPosition()));
        System.out.println("Новая перемещенная: " + newMovedCell + " на " +
                Arrays.toString(newMovedCell.getPosition()));

        updateDecoratorsAfterMove(cell, newMovedCell);
        NotifySubscribers();
    }



    private void updateDecoratorsAfterMove(Cell oldCell, Cell newCell) {
        LandscapeCellDecorator oldDecorator = landscapeDecorators.stream()
                .filter(d -> d.cell.equals(oldCell))
                .findFirst()
                .orElse(null);

        landscapeDecorators.removeIf(d -> d.cell.equals(oldCell));

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
    }

    public boolean isAdjacent(Cell a, Cell b) {
        int[] posA = a.getPosition();
        int[] posB = b.getPosition();

        // Вычисляем разницу по X и Y
        int dx = Math.abs(posA[0] - posB[0]);
        int dy = Math.abs(posA[1] - posB[1]);

        // Клетки соседние, если разница по одной координате равна 1, а по другой - 0
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    public Cell getEmptyCell() {
        for (java.util.List<Cell> row : _cells)
            for (Cell cell : row)
                if (cell.getIsEmpty()) return cell;
        return null;
    }


    public List<List<Cell>> getСells() {
        return this._cells;
    }

    public List<IGameFieldListener> _subscribers = new ArrayList<>();

    public void AddSubscribers(IGameFieldListener subscriber){
        _subscribers.add(subscriber);
    }

    public void RemoveSubscribers(IGameFieldListener subscriber){
        _subscribers.remove(subscriber);
    }

    private void NotifySubscribers(){
        for(IGameFieldListener obj : _subscribers){
            obj.CellMoved(_cells);
        }
    }
}
