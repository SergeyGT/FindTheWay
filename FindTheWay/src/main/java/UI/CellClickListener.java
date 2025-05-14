package UI;


import org.example.Cell;

@FunctionalInterface
public interface CellClickListener {
    void onCellClick(Cell cell);
}