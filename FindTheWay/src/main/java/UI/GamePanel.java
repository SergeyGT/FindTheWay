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
                panel.add(button);
            }
        }

        panel.revalidate();
        panel.repaint();
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
