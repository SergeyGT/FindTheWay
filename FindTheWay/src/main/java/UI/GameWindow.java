package UI;
import org.example.Cell;
import org.example.GameField;
import org.example.GameManager;
import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private final GameManager gameManager;
    private final GamePanel gamePanel;
    private JLabel movesLabel;
    private int movesCount;
    private final Timer gameStatusTimer;

    public GameWindow(GameManager gameManager) {
        this.gameManager = gameManager;
        this.movesCount = 0;

        ImageManager imageManager = new ImageManager();
        CellButtonFactory buttonFactory = new CellButtonFactory(imageManager);
        this.gamePanel = new GamePanel(gameManager, buttonFactory);
        gamePanel.setCellClickListener(this::handleCellClick);

        gameStatusTimer = new Timer(500, e -> checkGameStatus());
        gameStatusTimer.start();

        initializeUI();
    }

    private void checkGameStatus() {
        if (gameManager.isGameCompleted()) {
            gameStatusTimer.stop();
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                        this,
                        "Победа! Ходов: " + movesCount,
                        "Игра завершена",
                        JOptionPane.INFORMATION_MESSAGE
                );
                restartGame();
            });

        }
    }

    private void restartGame() {
        gameManager.StartGame(); // Пересоздаем игру через GameManager
        movesCount = 0;
        movesLabel.setText("Ходы: 0");
        gamePanel.update();
        gameStatusTimer.start(); // Перезапускаем таймер проверки статуса
    }

    private void handleCellClick(Cell cell) {
        // Реальная обработка клика
        GameField field = gameManager.getField();
        Cell emptyCell = field.getEmptyCell();

        if (emptyCell != null && isAdjacent(cell, emptyCell)) {
            field.MoveCell(cell);
            movesCount++;
            movesLabel.setText("Ходы: " + movesCount);
            gamePanel.update();

            // Проверяем условие победы
            if (gameManager.getMaze().CheckMazeCondition(field.getСells())) {
                // Уведомление придёт через MazeCompletionListener
            }
        }
    }

    private void initializeUI() {
        setTitle("Path Puzzle Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель информации
        movesLabel = new JLabel("Ходы: 0");
        add(movesLabel, BorderLayout.NORTH);

        // Игровое поле
        add(gamePanel.getPanel(), BorderLayout.CENTER);

        // Кнопка рестарта
        JButton restartButton = new JButton("Новая игра");
        restartButton.addActionListener(e -> restartGame());
        add(restartButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean isAdjacent(Cell a, Cell b) {
        int[] posA = a.getPosition();
        int[] posB = b.getPosition();
        int dx = Math.abs(posA[0] - posB[0]);
        int dy = Math.abs(posA[1] - posB[1]);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    public static void showGameWindow(GameManager gameManager) {
        SwingUtilities.invokeLater(() -> new GameWindow(gameManager));
    }
}