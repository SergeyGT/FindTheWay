package org.example;

import Interfaces.IMaze;
import lombok.Data;

@Data
public class GameManager {
    private GameField _field;
    private Maze _maze;
    private boolean _gameStatus;

    public void StartGame(){
        ResetGame();
        _gameStatus = false;
        AddDependencies();
        CreateField();
    }

    private void AddDependencies(){
        _maze = new Maze();
        _field.AddSubscribers(_maze);
        MazeObserver observer = new MazeObserver();
        _maze.AddSubscribers(observer);
    }

    private void ResetGame(){
        _gameStatus = false;
        _field = null;
        _maze = null;
    }

    private void EndGame(){
        _gameStatus = true;
    }

    private void CreateField(){
        _field = new GameField(6,6);
    }

    private class MazeObserver implements IMaze {

        @Override
        public void OnMaseComplete() {
            EndGame();
        }
    }
}
