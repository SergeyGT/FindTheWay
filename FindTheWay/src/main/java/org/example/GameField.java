package org.example;

import Factories.CellFactory;
import Interfaces.IGameFieldListener;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.shuffle;

@Getter
@Setter
public class GameField {
    private int _width;
    private int _height;
    private List<List<Cell>> _cells;


    public GameField(int width, int height){
        _width = width;
        _height = height;
    }

    public void loadFromLevel(String filePath) {
        List<List<Cell>> loadedCells = LevelLoader.loadFromJson(filePath);
        _height = loadedCells.size();
        _width = loadedCells.get(0).size();
        _cells = loadedCells;
    }

    public void MoveCell(Cell cell){
        Cell emptyCell = getEmptyCell();
        if (emptyCell == null) return;

        int[] pos = cell.getPosition();
        int[] emptyPos = emptyCell.getPosition();

        int dx = Math.abs(pos[0] - emptyPos[0]);
        int dy = Math.abs(pos[1] - emptyPos[1]);

        if ((dx == 1 && dy == 0) || (dx == 0 && dy == 1)) {
            swapCells(pos[0], pos[1], emptyPos[0], emptyPos[1]);
            NotifySubscribers();
        }
    }

    private void swapCells(int x1, int y1, int x2, int y2) {
        Cell cell1 = _cells.get(y1).get(x1);
        Cell cell2 = _cells.get(y2).get(x2);

        boolean isEmpty1 = cell1.getIsEmpty();
        boolean isEmpty2 = cell2.getIsEmpty();

        cell1.setIsEmpty(isEmpty2);
        cell2.setIsEmpty(isEmpty1);

        cell1.setPosition(x2, y2);
        cell2.setPosition(x1, y1);

        _cells.get(y1).set(x1, cell2);
        _cells.get(y2).set(x2, cell1);
    }

    public Cell getEmptyCell() {
        for (java.util.List<Cell> row : _cells)
            for (Cell cell : row)
                if (cell.getIsEmpty()) return cell;
        return null;
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
