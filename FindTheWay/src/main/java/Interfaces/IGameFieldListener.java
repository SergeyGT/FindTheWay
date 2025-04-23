package Interfaces;

import java.util.List;
import org.example.Cell;

public interface IGameFieldListener {
    public void CellMoved(List<List<Cell>> cell);
}
