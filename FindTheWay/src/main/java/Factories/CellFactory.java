package Factories;


import org.example.Cell;
import org.example.Direction;

public class CellFactory {
    public Cell createCell(int x, int y, boolean isEmpty, Direction directionEnter, Direction directionExit) {
        return new Cell(x, y, isEmpty, directionEnter, directionExit);
    }
}