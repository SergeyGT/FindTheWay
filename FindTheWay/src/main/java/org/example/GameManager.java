package org.example;

import Interfaces.IMaze;
import lombok.Data;

@Data
public class GameManager {
    private GameField _field;
    private Maze _maze;
    private boolean _gameStatus;

    public void StartGame(){
        _gameStatus = false;
        CreateField();
    }

    private void EndGame(){
        _gameStatus = true;
    }

    private void CreateField(){
        _field = new GameField(5,5);
    }

    private class MazeObserver implements IMaze {

        @Override
        public void OnMaseComplete() {
            EndGame();
        }
    }
}
