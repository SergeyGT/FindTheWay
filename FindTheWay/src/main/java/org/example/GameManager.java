package org.example;

import Interfaces.IMazeListener;
import lombok.Data;

@Data
public class GameManager {
    private GameField _field;
    private Maze _maze;
    private boolean _gameStatus;

    public void StartGame(){
        ResetGame();
        CreateField();
        AddDependencies();
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
        _field = new GameField(2,2);
        _field.loadFromLevel("src/main/resources/levels/level1.json");
    }

    public class MazeObserver implements IMazeListener {

        @Override
        public void OnMaseComplete() {
            EndGame();
        }
    }
}
