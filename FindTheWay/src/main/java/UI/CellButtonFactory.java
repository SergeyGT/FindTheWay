package UI;


import org.example.Cell;
import org.example.Direction;
import javax.swing.*;
import java.awt.*;

public class CellButtonFactory {
    private final ImageManager imageManager;

    public CellButtonFactory(ImageManager imageManager) {
        this.imageManager = imageManager;
    }

    public JButton createCellButton(Cell cell, CellClickListener listener) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(80, 80));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        if (cell.getIsEmpty()) {
            configureEmptyButton(button);
        } else {
            configureNonEmptyButton(button, cell, listener);
        }

        return button;
    }

    private void configureEmptyButton(JButton button) {
        button.setIcon(imageManager.getEmptyIcon());
        button.setBackground(new Color(230, 230, 230));
        button.setEnabled(false);
    }

    private void configureNonEmptyButton(JButton button, Cell cell, CellClickListener listener) {
        if (cell.isStart()) {
            button.setIcon(imageManager.getStartIcon());
            button.setBackground(new Color(144, 238, 144));
            button.setEnabled(false);
            button.setToolTipText("Начальная точка");
        } else if (cell.isEnd()) {
            button.setIcon(imageManager.getEndIcon());
            button.setBackground(new Color(255, 182, 193));
            button.setEnabled(false);
            button.setToolTipText("Конечная точка");
        } else {
            // Получаем направления входа и выхода
            Direction enter = cell.getDirectionEnter();
            Direction exit = cell.getDirectionExit();

            // Определяем ключ для карты изображений
            String iconKey = determineIconKey(enter, exit);

            // Устанавливаем соответствующее изображение
            if (iconKey != null && imageManager.getDirectionIcons().containsKey(iconKey)) {
                button.setIcon(imageManager.getDirectionIcons().get(iconKey));
            } else {
                // Запасной вариант, если изображение не найдено
                button.setText("?");
            }
            button.setBackground(Color.WHITE);
        }
        button.addActionListener(e -> listener.onCellClick(cell));

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(cell.isStart() || cell.isEnd() ?
                        new Color(100, 100, 100) : Color.BLACK, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    private String determineIconKey(Direction enter, Direction exit) {
        if (enter == null || exit == null) {
            return null;
        }

        // Формируем ключ в формате "вход_выход" (например, "left_up")
        String enterDir = enter.getDirectionEnum().toString().toLowerCase();
        String exitDir = exit.getDirectionEnum().toString().toLowerCase();

        return enterDir + exitDir; // или другой формат, соответствующий вашим именам файлов
    }
}
