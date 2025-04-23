package org.example;

import Interfaces.IMazeListener;
import lombok.Data;



@Data
public class GameManager {
    private GameField _field;
    private Maze _maze;
    private GameStatus gameStatus;

    public void StartGame(){
        ResetGame();
        CreateMaze();
        CreateField();
    }

    private void CreateMaze(){
        _maze = new Maze();
        MazeObserver observer = new MazeObserver();
        _maze.AddSubscribers(observer);
    }

    private void ResetGame(){
        gameStatus = GameStatus.INPROCESS;
        _field = null;
        _maze = null;
    }

    private void EndGame(){
        gameStatus = GameStatus.ENDGAME;
    }

    private void CreateField(){
        _field = new GameField(2,2);
        _field.loadFromLevel("src/main/resources/levels/level1.json");

        _field.AddSubscribers(_maze);
    }

    public class MazeObserver implements IMazeListener {

        @Override
        public void OnMaseComplete() {
            EndGame();
        }
    }
}
