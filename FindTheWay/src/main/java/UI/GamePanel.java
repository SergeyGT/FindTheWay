package UI;

import org.example.*;
import javax.swing.*;
import java.awt.*;

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
        LandscapeManager landscapeManager = gameManager.getLandscapeManager();

        panel.setLayout(new GridLayout(field.getHeight(), field.getWidth()));

        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                Cell cell = field.getСells().get(y).get(x);
                JButton button = buttonFactory.createCellButton(cell, this::handleCellClick);

                updateButtonAppearance(button, cell, landscapeManager);

                panel.add(button);
            }
        }

        panel.revalidate();
        panel.repaint();
    }

    private void updateButtonAppearance(JButton button, Cell cell, LandscapeManager landscapeManager) {
        button.setBackground(Color.WHITE);

        if (cell.getLandscapeType() != null) {
            switch (cell.getLandscapeType().toLowerCase()) {
                case "tree":
                    button.setBackground(new Color(67, 39, 15));
                    break;
                case "fire":
                    button.setBackground(Color.RED);
                    boolean canMoveFire = landscapeManager.getDecorator(cell) == null
                            || !(landscapeManager.getDecorator(cell).landscapeElement instanceof Fire)
                            || ((Fire) landscapeManager.getDecorator(cell).landscapeElement).canMove();
                    if (!canMoveFire) button.setBackground(Color.CYAN);
                    button.setEnabled(canMoveFire);
                    break;
                case "flowerbed":
                    button.setBackground(Color.PINK);
                    button.setEnabled(false);

                    LandscapeCellDecorator decorator = landscapeManager.getDecorator(cell);
                    if (decorator != null && decorator.landscapeElement instanceof FlowerBed) {
                        FlowerBed fb = (FlowerBed) decorator.landscapeElement;
                        if (!fb.isWatered()) {
                            button.setBackground(new Color(255, 182, 150));
                        }
                        if (!fb.isAlive()) {
                            button.setBackground(Color.GRAY);
                            button.setEnabled(true); // можно удалить мертвую клумбу
                        }
                    }
                    break;
                case "grass":
                    button.setBackground(Color.GREEN);
                    break;
                case "water":
                    button.setBackground(Color.BLUE);
                    break;
                case "grassroad":
                    button.setBackground(new Color(1, 52, 32));
                    break;
                case "burnt":
                    button.setBackground(Color.DARK_GRAY);
                    button.setEnabled(false);
                    break;
            }
        }

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

