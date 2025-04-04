package org.example;

import lombok.Data;


enum Direction{
    SOUTH,
    NORTH,
    WEST,
    EAST
}
@Data
public class Cell {
    private int posX;
    private int posY;
    private Direction _directionEnter;
    private Direction _directionExit;
    private boolean _isEmpty;

    public boolean CanConnect(Cell cell){
        if (cell == null || this.is_isEmpty() || cell.is_isEmpty()) {
            System.out.println("Cell is null or empty");
            return false;
        }

        return this._directionExit == cell.InverseDirection(cell._directionEnter);
    }

    private Direction InverseDirection(Direction direction){
        return switch (direction) {
            case WEST -> Direction.EAST;
            case EAST -> Direction.WEST;
            case SOUTH -> Direction.NORTH;
            case NORTH -> Direction.SOUTH;
            default -> throw new RuntimeException("Нет направления");
        };
    }
}
