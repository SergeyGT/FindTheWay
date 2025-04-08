package org.example;

import Interfaces.IGameField;
import Interfaces.IMaze;

import java.util.List;

import static org.example.DirectionEnum.*;

public class Maze implements IGameField {
    private boolean CheckTargetState(List<List<Cell>> cells){
        // Проход по всем ячейкам построчно
        for (int y = 0; y < cells.size(); y++) {
            for (int x = 0; x < cells.get(y).size(); x++) {
                Cell current = cells.get(y).get(x);

                // Пропускаем пустые ячейки
                if (current.getIsEmpty()) continue;

                Direction exit = current.getDirectionExit();
                if (exit == null) continue;

                int nextX = x, nextY = y;
                switch (exit.getDirectionEnum()) {
                    case UP: nextY -= 1; break;
                    case DOWN: nextY += 1; break;
                    case LEFT: nextX -= 1; break;
                    case RIGHT: nextX += 1; break;
                }
                if (nextX < 0 || nextY < 0 || nextY >= cells.size() || nextX >= cells.get(nextY).size()) {
                    return false;
                }

                Cell nextCell = cells.get(nextY).get(nextX);
                if (nextCell.getIsEmpty()) return false;

                Direction enter = nextCell.getDirectionEnter();
                if (enter == null || !exit.canConnect(enter)) {
                    return false;
                }
            }
        }

        // Если всё прошло — путь правильный
        NotifySubscribers(); // Уведомляем о завершении
        return true;
    }

    @Override
    public void CellMoved(List<List<Cell>> cells) {
        if (CheckTargetState(cells)) {
            System.out.println("Путь собран!");
        }
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
