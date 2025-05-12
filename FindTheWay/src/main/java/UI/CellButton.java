package UI;import org.example.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CellButton extends JButton {
    private Cell cell;
    private final ActionListener clickListener;

    public CellButton(Cell cell, ActionListener clickListener) {
        this.cell = cell;
        this.clickListener = clickListener;
        configureButton();
        updateAppearance();
        setupClickListener();
    }

    private void configureButton() {
        setPreferredSize(new Dimension(80, 80));
        setFont(new Font("Arial", Font.BOLD, 24));
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setContentAreaFilled(true);
        setOpaque(true);
    }

    public void updateCell(Cell newCell) {
        System.out.printf("Обновление кнопки: %s -> %s (empty=%b)%n",
                cellToString(this.cell),
                cellToString(newCell),
                newCell.getIsEmpty());

        this.cell = newCell;
        updateAppearance();
    }

    private String cellToString(Cell cell) {
        return String.format("[%d,%d]", cell.getPosition()[0], cell.getPosition()[1]);
    }

    public void updateAppearance() {
        // Сброс предыдущего состояния
        setText("");
        setBackground(Color.WHITE);
        setEnabled(true);

        if (cell.getIsEmpty()) {
            setBackground(new Color(230, 230, 230)); // Светло-серый
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        } else {
            setText(getCellSymbol());
            setBackground(getCellColor());
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        }
    }

    private String getCellSymbol() {
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

    private Color getCellColor() {
        if (cell.isStart()) return new Color(144, 238, 144); // Светло-зеленый
        if (cell.isEnd()) return new Color(255, 182, 193);  // Светло-розовый
        return Color.WHITE;
    }

    private void setupClickListener() {
        addActionListener(e -> {
            if (!cell.getIsEmpty()) { // Игнорируем клики по пустым клеткам
                clickListener.actionPerformed(
                        new ActionEvent(this, ActionEvent.ACTION_PERFORMED, cell.toString())
                );
            }
        });
    }

    public Cell getCell() {
        return cell;
    }
}