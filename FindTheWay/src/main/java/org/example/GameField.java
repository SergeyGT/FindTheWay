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
    private final List<IGameFieldListener> _subscribers = new ArrayList<>();

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
        _cells = LevelLoader.loadFromJson(filePath);
        _height = _cells.size();
        _width = _cells.get(0).size();
    }


    public void MoveCell(Cell cell) {
        Cell empty = getEmptyCell();
        if (empty == null || !isAdjacent(cell, empty)) return;

        Cell newEmpty = createEmptyCell(cell);
        Cell newMoved = createMovedCell(empty, cell);

        updateCells(cell, empty, newEmpty, newMoved);
        NotifySubscribers();
    }

    private void updateCells(Cell oldCell, Cell emptyCell, Cell newEmpty, Cell newMoved) {
        int[] oldPos = oldCell.getPosition();
        int[] emptyPos = emptyCell.getPosition();

        _cells.get(oldPos[1]).set(oldPos[0], newEmpty);
        _cells.get(emptyPos[1]).set(emptyPos[0], newMoved);
    }

    private Cell createMovedCell(Cell target, Cell source) {
        int[] pos = target.getPosition();
        Cell newCell = new Cell(pos[0], pos[1]);

        newCell.setIsEmpty(false);
        newCell.setLandscapeType(source.getLandscapeType());
        newCell.set_directionEnter(source.get_directionEnter());
        newCell.set_directionExit(source.get_directionExit());
        newCell.setStart(source.isStart());
        newCell.setEnd(source.isEnd());

        return newCell;
    }

    private Cell createEmptyCell(Cell oldCell) {
        int[] pos = oldCell.getPosition();
        Cell newCell = new Cell(pos[0], pos[1]);
        newCell.setIsEmpty(true);
        return newCell;
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
