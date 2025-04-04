package org.example;

import Interfaces.IGameField;
import Interfaces.IMaze;
import lombok.Data;

import java.util.List;

@Data
public class GameField {
    private int _width;
    private int _height;
    private List<List<Cell>> _cells;

    private void MoveCell(Cell cell){
        if(CheckPossibilityMove(cell)) {

        }
    }

    private boolean CheckPossibilityMove(Cell cell){
        return true;
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
