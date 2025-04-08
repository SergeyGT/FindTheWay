package org.example;

import lombok.Data;


@Data
public class Cell {
    private int posX;
    private int posY;
    private Direction _directionEnter;
    private Direction _directionExit;
    private boolean _isEmpty;

}
