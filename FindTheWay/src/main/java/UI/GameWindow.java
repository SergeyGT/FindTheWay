package UI;
import org.example.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class GameWindow extends JFrame {
    private final GameManager gameManager;
    private  JPanel gamePanel;
    private JLabel movesLabel;
    private int movesCount;

    public GameWindow(GameManager gameManager) {
        this.gameManager = gameManager;
        this.movesCount = 0;
        initializeUI();
        printInitialState(); // Отладочный вывод
    }

    private void initializeUI() {
        setTitle("Path Puzzle Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель информации
        movesLabel = new JLabel("Ходы: 0");
        add(movesLabel, BorderLayout.NORTH);

        // Игровое поле
        gamePanel = new JPanel();
        updateGamePanel(); // Первоначальное создание поля
        add(gamePanel, BorderLayout.CENTER);

        // Кнопка рестарта
        JButton restartButton = new JButton("Новая игра");
        restartButton.addActionListener(e -> restartGame());
        add(restartButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateGamePanel() {
        gamePanel.removeAll();
        GameField field = gameManager.getField();
        gamePanel.setLayout(new GridLayout(field.getHeight(), field.getWidth()));

        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                Cell cell = field.getСells().get(y).get(x);
                JButton button = createCellButton(cell);
                gamePanel.add(button);
            }
        }

        gamePanel.revalidate();
        gamePanel.repaint();
        printCurrentState(); // Отладочный вывод
    }

    private JButton createCellButton(Cell cell) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(80, 80));
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setOpaque(true);

        if (cell.getIsEmpty()) {
            button.setBackground(new Color(230, 230, 230)); // Светло-серый
            button.setEnabled(false);
        } else {
            button.setText(getCellText(cell));
            button.setBackground(getCellColor(cell));
            button.addActionListener(e -> handleCellClick(cell));
        }

        return button;
    }

    private String getCellText(Cell cell) {
        if (cell.isStart()) return "S";
        if (cell.isEnd()) return "E";

        Direction exit = cell.getDirectionExit();
        if (exit != null) {
            return switch (exit.getDirectionEnum()) {
                case UP -> "↑";
                case DOWN -> "↓";
                case LEFT -> "←";
                case RIGHT -> "→";
            };
        }
        return "";
    }

    private Color getCellColor(Cell cell) {
        if (cell.isStart()) return new Color(144, 238, 144); // Светло-зеленый
        if (cell.isEnd()) return new Color(255, 182, 193);  // Светло-розовый
        return Color.WHITE;
    }

    private void handleCellClick(Cell clickedCell) {
        GameField field = gameManager.getField();
        Cell emptyCell = field.getEmptyCell();

        System.out.println("\n--- ОБРАБОТКА КЛИКА ---");
        System.out.println("Кликнута клетка: " + Arrays.toString(clickedCell.getPosition()));
        System.out.println("Пустая клетка: " + Arrays.toString(emptyCell.getPosition()));

        if (emptyCell != null && isAdjacent(clickedCell, emptyCell)) {
            System.out.println("Выполняем перемещение...");

            // Меняем клетки местами в модели
            field.MoveCell(clickedCell);
            movesCount++;
            movesLabel.setText("Ходы: " + movesCount);

            // Полностью обновляем UI
            updateGamePanel();

            if (gameManager.getMaze().CheckMazeCondition(field.getСells())) {
                JOptionPane.showMessageDialog(this, "Победа! Ходов: " + movesCount);
            }
        }
    }

    private boolean isAdjacent(Cell a, Cell b) {
        int[] posA = a.getPosition();
        int[] posB = b.getPosition();
        int dx = Math.abs(posA[0] - posB[0]);
        int dy = Math.abs(posA[1] - posB[1]);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    private void restartGame() {
        gameManager.StartGame();
        movesCount = 0;
        movesLabel.setText("Ходы: 0");
        updateGamePanel();
    }

    private void printInitialState() {
        System.out.println("--- НАЧАЛЬНОЕ СОСТОЯНИЕ ---");
        printCurrentState();
    }

    private void printCurrentState() {
        GameField field = gameManager.getField();
        System.out.println("--- ТЕКУЩЕЕ СОСТОЯНИЕ ---");
        System.out.println("Пустая клетка: " + Arrays.toString(field.getEmptyCell().getPosition()));

        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                Cell cell = field.getСells().get(y).get(x);
                System.out.printf("[%d,%d]%s ", x, y, cell.getIsEmpty() ? " " : "X");
            }
            System.out.println();
        }
    }

    public static void showGameWindow(GameManager gameManager) {
        SwingUtilities.invokeLater(() -> new GameWindow(gameManager));
    }
}