package org.example;

import UI.GameWindow;

public class Main {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        gameManager.StartGame();
        GameWindow.showGameWindow(gameManager);
    }
}