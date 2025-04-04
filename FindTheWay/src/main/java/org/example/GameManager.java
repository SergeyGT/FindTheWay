package org.example;

import Interfaces.IMaze;
import lombok.Data;

@Data
public class GameManager {
    private GameField _field;
    private Maze _maze;
    private boolean _gameStatus;

    private void StartGame(){

    }

    private void EndGame(){
        //_field.RemoveSubscribers(_field.);
    }

    private void CreateField(){

    }

    private class MazeObserver implements IMaze {

        @Override
        public void OnMaseComplete() {
            EndGame();
        }
    }
}
