package UI;

import org.example.*;

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
        gameManager.StartGame();
        movesCount = 0;
        movesLabel.setText("Ходы: 0");
        gamePanel.update();
        gameStatusTimer.start();
    }

    private void handleCellClick(Cell cell) {
        if (!gameManager.canMoveCell(cell)) {
            JOptionPane.showMessageDialog(this, "Эту клетку нельзя перемещать!");
            return;
        }

        gameManager.moveCell(cell);
        movesCount++;
        movesLabel.setText("Ходы: " + movesCount);
        gameManager.update();
        gamePanel.update();
    }

    private void initializeUI() {
        setTitle("Path Puzzle Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setSize(700, 600);
        setResizable(false);

        movesLabel = new JLabel("Ходы: 0");
        add(movesLabel, BorderLayout.NORTH);

        add(gamePanel.getPanel(), BorderLayout.CENTER);

        JButton restartButton = new JButton("Новая игра");
        restartButton.addActionListener(e -> restartGame());
        add(restartButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void showGameWindow(GameManager gameManager) {
        SwingUtilities.invokeLater(() -> new GameWindow(gameManager));
    }
}
