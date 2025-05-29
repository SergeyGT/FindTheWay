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
                if (!cell.getIsEmpty()) {
                    // Здесь можно добавить логику создания разных типов ландшафтных элементов
                    // на основе каких-то условий или данных из клетки
                    ILandscapeElement element = new WildGrass(); // По умолчанию
                    landscapeDecorators.add(new LandscapeCellDecorator(cell, element));
                }
            }
        }
    }

    public void updateLandscapeElements() {
        for (LandscapeCellDecorator decorator : landscapeDecorators) {
            if (decorator != null) {
                // Получаем соседей для проверки окружения огнем
                List<LandscapeCellDecorator> neighbors = getNeighbors(decorator);
                decorator.checkFireSurrounding(neighbors);
                decorator.update();
            }
        }
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

        // Создаем НОВЫЕ объекты клеток вместо модификации существующих
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

        NotifySubscribers();
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

    private void swapCells(int x1, int y1, int x2, int y2) {
        // 1. Получаем ссылки на объекты клеток
        Cell cell1 = _cells.get(y1).get(x1);
        Cell cell2 = _cells.get(y2).get(x2);

        // 2. Меняем клетки местами в сетке
        _cells.get(y1).set(x1, cell2);
        _cells.get(y2).set(x2, cell1);

        // 3. Обновляем координаты клеток
        cell1.setPosition(x2, y2);  // Важно: сначала обновляем позиции
        cell2.setPosition(x1, y1);

        // 4. Меняем флаги empty
        boolean tempEmpty = cell1.getIsEmpty();
        cell1.setIsEmpty(cell2.getIsEmpty());
        cell2.setIsEmpty(tempEmpty);

        // 5. Валидация
        System.out.println("Проверка после swap:");
        System.out.println("Клетка 1 теперь: " + cell1 + " at " + Arrays.toString(cell1.getPosition()));
        System.out.println("Клетка 2 теперь: " + cell2 + " at " + Arrays.toString(cell2.getPosition()));
        System.out.println("В сетке на ["+x1+","+y1+"]: " + _cells.get(y1).get(x1));
        System.out.println("В сетке на ["+x2+","+y2+"]: " + _cells.get(y2).get(x2));
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
