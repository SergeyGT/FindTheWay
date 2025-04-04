package org.example;

import Interfaces.IGameField;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameField {
    private int _width;
    private int _height;
    private List<List<Cell>> _cells;


    public GameField(int width, int height){
        _width = width;
        _height = height;
    }

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
