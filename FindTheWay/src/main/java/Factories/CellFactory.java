package Factories;


import org.example.Cell;

public class CellFactory {
    public Cell createCell(int x, int y, boolean isEmpty) {
        return new Cell(x, y, isEmpty);
    }
}