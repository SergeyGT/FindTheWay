package UI;

import org.example.*;
import javax.swing.*;
import java.awt.*;

public class GamePanel {
    private final JPanel panel;
    private final GameManager gameManager;
    private final CellButtonFactory buttonFactory;
    private CellClickListener clickListener;

    public GamePanel(GameManager gameManager, CellButtonFactory buttonFactory) {
        this.gameManager = gameManager;
        this.buttonFactory = buttonFactory;
        this.panel = new JPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    public void update() {
        panel.removeAll();
        GameField field = gameManager.getField();
        panel.setLayout(new GridLayout(field.getHeight(), field.getWidth()));

        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                Cell cell = field.getСells().get(y).get(x);
                JButton button = buttonFactory.createCellButton(cell, this::handleCellClick);

                updateButtonAppearance(button, cell, field);
                //decorateButtonWithLandscape(button, cell, field);

                panel.add(button);
            }
        }

        panel.revalidate();
        panel.repaint();
    }

    private void updateButtonAppearance(JButton button, Cell cell, GameField field) {
        // Сброс к базовому состоянию
        button.setBackground(Color.WHITE);

        // Обработка ландшафта
        if (cell.getLandscapeType() != null) {
            switch (cell.getLandscapeType().toLowerCase()) {
                case "tree":
                    button.setBackground(new Color(67, 39, 15));
                    break;
                case "fire":
                    button.setBackground(Color.RED);
                    // Проверяем, можно ли перемещать этот огонь
                    boolean canMoveFire = field.getLandscapeDecorators().stream()
                            .filter(d -> d.cell.equals(cell))
                            .findFirst()
                            .map(d -> d.landscapeElement instanceof Fire && ((Fire)d.landscapeElement).canMove(d.cell))//cell
                            .orElse(true);
                    if(!canMoveFire) button.setBackground(Color.CYAN);
                    button.setEnabled(canMoveFire);

                    break;
                case "flowerbed":
                    button.setBackground(Color.PINK);
                    button.setEnabled(false); // Нельзя перемещать

                    // Проверяем состояние клумбы
                    field.getLandscapeDecorators().stream()
                            .filter(d -> d.cell.equals(cell) && d.landscapeElement instanceof FlowerBed)
                            .findFirst()
                            .ifPresent(d -> {
                                FlowerBed flowerBed = (FlowerBed) d.landscapeElement;
                                if (!flowerBed.isWatered()) {
                                    // Визуальный индикатор недостатка воды
                                    button.setBackground(new Color(255, 182, 150)); // Бледно-розовый
                                }
                                if (!flowerBed.isAlive()) {
                                    button.setBackground(Color.GRAY); // Мертвая клумба
                                    button.setEnabled(true);
                                }
                            });
                    break;
                case "grass":
                    button.setBackground(Color.GREEN);
                    break;
                case "water":
                    button.setBackground(Color.BLUE);
                    break;
                case "grassroad":
                    button.setBackground(new Color(1,52,32));
                    break;
                case "burnt":
                    button.setBackground(Color.DARK_GRAY);
                    button.setEnabled(false);
                    break;
            }
        }

        // Особые клетки (старт/финиш)
        if (cell.isStart()) button.setBackground(new Color(144, 238, 144));
        if (cell.isEnd()) button.setBackground(new Color(255, 182, 193));
    }


    public void setCellClickListener(CellClickListener listener) {
        this.clickListener = listener;
    }

    private void handleCellClick(Cell cell) {
        if (clickListener != null) {
            clickListener.onCellClick(cell);
        }
    }

}
