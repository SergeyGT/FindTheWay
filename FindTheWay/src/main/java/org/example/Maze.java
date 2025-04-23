package org.example;

import Interfaces.IGameFieldListener;
import Interfaces.IMazeListener;

import java.util.ArrayList;
import java.util.List;


public class Maze implements IGameFieldListener {
    public boolean CheckMazeCondition(List<List<Cell>> cells) {
        for (int y = 0; y < cells.size(); y++) {
            for (int x = 0; x < cells.get(y).size(); x++) {
                Cell current = cells.get(y).get(x);

                if (current.getIsEmpty()) continue;

                Direction exit = current.getDirectionExit();
                if (exit == null) {
                    if (!current.isEnd()) {
                        return false;
                    } else {
                        continue;
                    }
                }

                int nextX = x, nextY = y;
                switch (exit.getDirectionEnum()) {
                    case UP: nextY -= 1; break;
                    case DOWN: nextY += 1; break;
                    case LEFT: nextX -= 1; break;
                    case RIGHT: nextX += 1; break;
                }

                // выход за границы
                if (nextX < 0 || nextY < 0 || nextY >= cells.size() || nextX >= cells.get(nextY).size()) {
                    if (!current.isEnd()) {
                        return false;
                    } else {
                        continue;
                    }
                }

                Cell nextCell = cells.get(nextY).get(nextX);
                if (nextCell.getIsEmpty()) return false;

                Direction enter = nextCell.getDirectionEnter();
                if (enter == null && !nextCell.isStart()) {
                    return false;
                }

                if (enter != null && !exit.canConnect(enter)) {
                    return false;
                }
            }
        }

        NotifySubscribers();
        return true;
    }


    @Override
    public void CellMoved(List<List<Cell>> cells) {
        if (CheckMazeCondition(cells)) {
            System.out.println("Путь собран!");
        }
    }

    private List<IMazeListener> _subscribers = new ArrayList<>();

    public void AddSubscribers(IMazeListener subscriber){
        _subscribers.add(subscriber);
    }

    public void RemoveSubscribers(IMazeListener subscriber){
        _subscribers.remove(subscriber);
    }

    private void NotifySubscribers(){
        for(IMazeListener obj : _subscribers){
            obj.OnMaseComplete();
        }
    }
}
