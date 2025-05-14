package org.example;

import Interfaces.IGameFieldListener;
import Interfaces.IMazeListener;

import java.util.ArrayList;
import java.util.List;


public class Maze implements IGameFieldListener {
    public boolean CheckMazeCondition(List<List<Cell>> cells) {
        Cell start = null, end = null;

        // Находим старт и финиш
        for (List<Cell> row : cells) {
            for (Cell cell : row) {
                if (cell.isStart()) start = cell;
                if (cell.isEnd()) end = cell;
            }
        }

        if (start == null || end == null) return false;

        // Проверяем путь
        Cell current = start;
        while (current != null && !current.equals(end)) {
            Direction exit = current.getDirectionExit();
            if (exit == null) return false;

            int nextX = current.getPosition()[0];
            int nextY = current.getPosition()[1];

            switch (exit.getDirectionEnum()) {
                case UP: nextY--; break;
                case DOWN: nextY++; break;
                case LEFT: nextX--; break;
                case RIGHT: nextX++; break;
            }

            // Проверка границ
            if (nextX < 0 || nextY < 0 || nextY >= cells.size() || nextX >= cells.get(nextY).size()) {
                return false;
            }

            Cell next = cells.get(nextY).get(nextX);
            if (next.getIsEmpty() || !exit.canConnect(next.getDirectionEnter())) {
                return false;
            }

            current = next;
        }

        // Уведомляем подписчиков о победе
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
