package org.example;

import lombok.Data;

import java.util.Objects;

@Data
public class Cell {
    private int posX;
    private int posY;
    private Direction _directionEnter;
    private Direction _directionExit;
    private boolean _isEmpty;
    private boolean isStart;
    private boolean isEnd;

    public Cell(int posX, int posY, boolean isEmpty, boolean isStart, boolean isEnd, Direction directionEnter, Direction directionExit) {
        this.posX = posX;
        this.posY = posY;
        this._isEmpty = isEmpty;
        this.isStart = isStart;
        this.isEnd = isEnd;
        this._directionEnter = directionEnter;
        this._directionExit = directionExit;
    }

    public int[] getPosition() {
        return new int[] { posX, posY };
    }

    public void setPosition(int x, int y) {
        // Добавляем проверку
        if (this.posX == x && this.posY == y) {
            System.out.println("Предупреждение: позиция не изменилась!");
        }
        this.posX = x;
        this.posY = y;
        System.out.println("Установлена позиция: " + this + " -> [" + x + "," + y + "]");
    }

    public boolean getIsEmpty(){
        return this._isEmpty;
    }

    public void setIsEmpty(boolean _isEmpty){
        this._isEmpty = _isEmpty;
    }

    public Direction getDirectionExit() {
        return this._directionExit;
    }

    public Direction getDirectionEnter() {
        return this._directionEnter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return posX == cell.posX && posY == cell.posY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }
}
