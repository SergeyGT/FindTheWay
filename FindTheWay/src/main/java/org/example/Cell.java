package org.example;

import lombok.Data;


@Data
public class Cell {
    private int posX;
    private int posY;
    private Direction _directionEnter;
    private Direction _directionExit;
    private boolean _isEmpty;

    public Cell(int posX, int posY, boolean isEmpty) {
        this.posX = posX;
        this.posY = posY;
        this._isEmpty = isEmpty;
    }

    public int[] getPosition() {
        return new int[] { posX, posY };
    }

    public void setPosition(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    public boolean getIsEmpty(){
        return this._isEmpty;
    }

    public Direction getDirectionExit() {
        return this._directionExit;
    }

    public Direction getDirectionEnter() {
        return this._directionEnter;
    }
}
