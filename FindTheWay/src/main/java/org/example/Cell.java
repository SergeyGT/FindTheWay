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
        return true;
    }
}
