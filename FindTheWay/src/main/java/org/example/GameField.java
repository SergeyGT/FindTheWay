package org.example;

import Factories.CellFactory;
import Interfaces.IGameField;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameField {
    private int _width;
    private int _height;
    private List<List<Cell>> _cells;


    public GameField(int width, int height){
        _width = width;
        _height = height;
        generateField();
    }

    private void generateField() {
        CellFactory factory = new CellFactory();
        _cells.clear();

        List<int[]> positions = new java.util.ArrayList<>();
        for (int y = 0; y < _height; y++) {
            for (int x = 0; x < _width; x++) {
                positions.add(new int[] { x, y });
            }
        }

        java.util.Collections.shuffle(positions);
        int[] emptyPos = positions.get(0);

        for (int y = 0; y < _height; y++) {
            List<Cell> row = new java.util.ArrayList<>();
            for (int x = 0; x < _width; x++) {
                boolean isEmpty = (x == emptyPos[0] && y == emptyPos[1]);
                row.add(factory.createCell(x, y, isEmpty));
            }
            _cells.add(row);
        }
    }

    private void MoveCell(Cell cell){
        Cell emptyCell = getEmptyCell();
        if (emptyCell == null) return;

        int[] pos = cell.getPosition();
        int[] emptyPos = emptyCell.getPosition();

        int dx = Math.abs(pos[0] - emptyPos[0]);
        int dy = Math.abs(pos[1] - emptyPos[1]);

        if ((dx == 1 && dy == 0) || (dx == 0 && dy == 1)) {
            swapCells(pos[0], pos[1], emptyPos[0], emptyPos[1]);
        }
    }

    private void swapCells(int x1, int y1, int x2, int y2) {
        Cell cell1 = _cells.get(y1).get(x1);
        Cell cell2 = _cells.get(y2).get(x2);

        int tempX = cell1.getPosition()[0];
        int tempY = cell1.getPosition()[1];

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

    public List<IGameField> _subscribers;

    public void AddSubscribers(IGameField subscriber){
        _subscribers.add(subscriber);
    }

    public void RemoveSubscribers(IGameField subscriber){
        _subscribers.remove(subscriber);
    }

    private void NotifySubscribers(){
        for(IGameField obj : _subscribers){
            obj.CellMoved(_cells);
        }
    }
}
