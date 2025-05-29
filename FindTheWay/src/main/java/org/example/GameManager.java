package org.example;

import Interfaces.IMazeListener;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class GameManager {
    private GameField _field;
    private Maze _maze;
    private GameStatus gameStatus;
    private List<String> _levelsPath = new ArrayList<>();
    private Level level;

    public GameManager(){
        _levelsPath.add("levelEasy.json");
        _levelsPath.add("level1.json");
        _levelsPath.add("level2.json");
    }

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
        gameStatus = GameStatus.WIN;
    }

    private void nextLevel(){

    }

    public void update() {
        _field.updateLandscapeElements();

        // Проверяем изменения после обновления
        if (_maze.CheckMazeCondition(_field.getСells())) {
            gameStatus = GameStatus.WIN;
        }
    }

    public boolean isGameCompleted(){
        if(gameStatus == GameStatus.WIN){
            return true;
        }
        return false;
    }

    private void CreateField(){
        _field = new GameField(2,2);

        _field.loadFromLevel("C:\\Java\\FindTheWay\\FindTheWay\\src\\main\\resources\\levels\\levelEasy.json");

        _field.AddSubscribers(_maze);
    }

    public GameField getField(){
        return _field;
    }

    public Maze getMaze()
    {
        return _maze;
    }


    public class MazeObserver implements IMazeListener {

        @Override
        public void OnMaseComplete() {
            EndGame();
        }
    }
}
