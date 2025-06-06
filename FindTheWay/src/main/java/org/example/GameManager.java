package org.example;

import Interfaces.IMazeListener;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private GameField _field;
    private Maze _maze;
    private GameStatus gameStatus;
    private final List<String> _levelsPath = new ArrayList<>();
    private Level level;
    private LandscapeManager landscapeManager;

    public GameManager(){
        _levelsPath.add("levelEasy.json");
        _levelsPath.add("level1.json");
        _levelsPath.add("level2.json");
    }

    public void StartGame(){
        ResetGame();
        CreateMaze();
        CreateField();
        InitializeLandscape();
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
        landscapeManager = null;
    }

    private void EndGame(){
        gameStatus = GameStatus.WIN;
    }

    public void update() {
        landscapeManager.update();

        if (_maze.CheckMazeCondition(_field.getСells())) {
            gameStatus = GameStatus.WIN;
        }
    }

    public boolean isGameCompleted(){
        return gameStatus == GameStatus.WIN;
    }

    private void CreateField(){
        _field = new GameField(2, 2);
        _field.loadFromLevel("resources/levels/levelWildGrassRoad.json");
        _field.AddSubscribers(_maze);
    }

    private void InitializeLandscape() {
        landscapeManager = new LandscapeManager();
        landscapeManager.initialize(_field.getСells());
    }

    public void moveCell(Cell cell) {
        Cell empty = _field.getEmptyCell();
        if (empty == null || !_field.isAdjacent(cell, empty)) return;

        if (!canMoveCell(cell)) return;

        landscapeManager.handleMove(cell, empty);
        _field.MoveCell(cell); // отвечает только за смену состояний ячеек
    }

    public boolean canMoveCell(Cell cell) {
        return !cell.getIsEmpty() && landscapeManager.canMove(cell);
    }

    public LandscapeManager getLandscapeManager() {
        return landscapeManager;
    }

    public GameField getField() {
        return _field;
    }

    public Maze getMaze() {
        return _maze;
    }

    public class MazeObserver implements IMazeListener {
        @Override
        public void OnMaseComplete() {
            EndGame();
        }
    }
}

