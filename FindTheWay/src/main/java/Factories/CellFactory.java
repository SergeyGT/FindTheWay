package Factories;


import org.example.Cell;

class CellFactory {
    public Cell createCell(boolean isEmpty) {
        Cell c = new Cell();
        // initialize c
        return c;
    }
}