import org.example.Cell;
import org.example.GameField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameFieldTests {
    private GameField gameField;

    @BeforeEach
    public void setUp() {
        gameField = new GameField(3, 3);
        gameField.loadFromLevel("src/main/resources/levels/test.json");
    }

    @Test
    public void testEmptyCellExistsAfterGeneration() {
        Cell emptyCell = gameField.getEmptyCell();
        assertNotNull(emptyCell, "На поле должна быть одна пустая ячейка ");
        assertTrue(emptyCell.getIsEmpty(), "Ячейка должна быть отмечена как пустая");
    }

    @Test
    public void testMoveAdjacentCellIntoEmptySpace() {
        Cell emptyCell = gameField.getEmptyCell();
        int emptyX = emptyCell.getPosition()[0];
        int emptyY = emptyCell.getPosition()[1];

        Cell neighbor = null;
        if (emptyX > 0)
            neighbor = gameField.get_cells().get(emptyY).get(emptyX - 1);
        else if (emptyX < 2)
            neighbor = gameField.get_cells().get(emptyY).get(emptyX + 1);
        else if (emptyY > 0)
            neighbor = gameField.get_cells().get(emptyY - 1).get(emptyX);
        else if (emptyY < 2)
            neighbor = gameField.get_cells().get(emptyY + 1).get(emptyX);

        assertNotNull(neighbor, "Соседняя ячейка должна существовать");

        int[] prevEmptyPos = emptyCell.getPosition();
        int[] prevNeighborPos = neighbor.getPosition();

        gameField.MoveCell(neighbor);

        assertNotEquals(prevEmptyPos[0], neighbor.getPosition()[0]);
        assertEquals(prevEmptyPos[1], neighbor.getPosition()[1]);
        assertNotEquals(prevNeighborPos[0], emptyCell.getPosition()[0]);
        assertEquals(prevNeighborPos[1], emptyCell.getPosition()[1]);

        assertFalse(neighbor.getIsEmpty(), "Соседняя ячейка должна стать пустой");
        assertTrue(emptyCell.getIsEmpty(), "Пустая ячейка теперь должна быть заполненной");
    }

    @Test
    public void testMoveNonAdjacentCellDoesNothing() {
        Cell emptyCell = gameField.getEmptyCell();

        Cell distantCell = null;
        for (List<Cell> row : gameField.get_cells()) {
            for (Cell cell : row) {
                if (!cell.getIsEmpty()) {
                    int dx = Math.abs(cell.getPosition()[0] - emptyCell.getPosition()[0]);
                    int dy = Math.abs(cell.getPosition()[1] - emptyCell.getPosition()[1]);
                    if (dx + dy > 1) {
                        distantCell = cell;
                        break;
                    }
                }
            }
            if (distantCell != null) break;
        }

        assertNotNull(distantCell, "Должна быть хотя бы одна не соседняя ячейка");

        int[] beforeDistant = distantCell.getPosition();
        int[] beforeEmpty = emptyCell.getPosition();

        gameField.MoveCell(distantCell);

        // Позиции не должны измениться
        assertArrayEquals(beforeDistant, distantCell.getPosition(), "Несоседняя ячейка не должна была переместиться");
        assertArrayEquals(beforeEmpty, emptyCell.getPosition(), "Пустая ячейка не должна была переместиться");
    }
}
