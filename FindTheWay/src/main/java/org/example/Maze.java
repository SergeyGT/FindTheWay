package org.example;

import Interfaces.IGameField;
import Interfaces.IMaze;

import java.util.List;

public class Maze implements IGameField {
    private boolean CheckTargetState(List<List<Cell>> cells){
        return true;
    }

    @Override
    public void CellMoved(List<List<Cell>> cell) {
        CheckTargetState(cell);
    }

    private List<IMaze> _subscribers;

    public void AddSubscribers(IMaze subscriber){
        _subscribers.add(subscriber);
    }

    public void RemoveSubscribers(IMaze subscriber){
        _subscribers.remove(subscriber);
    }

    private void NotifySubscribers(){
        for(IMaze obj : _subscribers){
            obj.OnMaseComplete();
        }
    }
}
