package Factories;


import org.example.Cell;
import org.example.Direction;

public class CellFactory {
    public Cell createCell(int x, int y, boolean isEmpty, boolean isStart, boolean isEnd, Direction directionEnter, Direction directionExit) {
        return new Cell(x, y, isEmpty, isStart, isEnd, directionEnter, directionExit);
    }
}