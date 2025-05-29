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

                decorateButtonWithLandscape(button, cell, field);

                panel.add(button);
            }
        }

        panel.revalidate();
        panel.repaint();
    }

    private void decorateButtonWithLandscape(JButton button, Cell cell, GameField field) {
        // Находим декоратор для этой клетки
        field.getLandscapeDecorators().stream()
                .filter(d -> d.cell.equals(cell))
                .findFirst()
                .ifPresent(decorator -> {
                    if (decorator.landscapeElement instanceof Tree) {
                        button.setBackground(new Color(34, 139, 34)); // Зеленый для дерева
                        if (((Tree) decorator.landscapeElement).isBurnt()) {
                            button.setBackground(new Color(139, 69, 19)); // Коричневый для сгоревшего дерева
                        }
                    } else if (decorator.landscapeElement instanceof Fire) {
                        button.setBackground(Color.RED);
                    } else if (decorator.landscapeElement instanceof FlowerBed) {
                        button.setBackground(Color.PINK);
                        if (!((FlowerBed) decorator.landscapeElement).isAlive()) {
                            button.setBackground(Color.GREEN);
                        }
                    } else if (decorator.landscapeElement instanceof WildGrass) {
                        button.setBackground(Color.GREEN);
                    }
                });
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
