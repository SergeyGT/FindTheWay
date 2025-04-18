import Interfaces.IMaze;
import org.example.Cell;
import org.example.Direction;
import org.example.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MazeTests {

    private Maze maze;
    private List<List<Cell>> cells;

    // Простая заглушка для подписчика
    private static class TestSubscriber implements IMaze {
        public boolean wasNotified = false;

        @Override
        public void OnMaseComplete() {
            wasNotified = true;
        }
    }

    @BeforeEach
    void setUp() {
        maze = new Maze();
        cells = new ArrayList<>();
    }

    @Test
    void testCheckTargetState_ValidPath() {
        // Создаем валидный лабиринт
        List<Cell> row1 = new ArrayList<>();
        row1.add(new Cell(false, new Direction(), new Direction()));
        row1.add(new Cell(false, new Direction(LEFT), new Direction(RIGHT)));

        List<Cell> row2 = new ArrayList<>();
        row2.add(new Cell(false, new Direction(UP), new Direction(DOWN)));
        row2.add(new Cell(false, new Direction(LEFT), new Direction(RIGHT)));

        cells.add(row1);
        cells.add(row2);

        assertTrue(maze.CheckTargetState(cells));
    }

    @Test
    void testCheckTargetState_InvalidPath() {
        // Создаем невалидный лабиринт
        List<Cell> row1 = new ArrayList<>();
        row1.add(new Cell(false, new Direction(UP), new Direction(DOWN)));
        row1.add(new Cell(false, new Direction(LEFT), new Direction(RIGHT)));

        List<Cell> row2 = new ArrayList<>();
        row2.add(new Cell(false, new Direction(UP), new Direction(UP))); // Несовместимое направление
        row2.add(new Cell(false, new Direction(LEFT), new Direction(RIGHT)));

        cells.add(row1);
        cells.add(row2);

        assertFalse(maze.CheckTargetState(cells));
    }

    @Test
    void testCheckTargetState_WithEmptyCells() {
        List<Cell> row1 = new ArrayList<>();
        row1.add(new Cell(true, null, null)); // Пустая ячейка
        row1.add(new Cell(false, new Direction(LEFT), new Direction(RIGHT)));

        List<Cell> row2 = new ArrayList<>();
        row2.add(new Cell(false, new Direction(UP), new Direction(DOWN)));
        row2.add(new Cell(false, new Direction(LEFT), new Direction(RIGHT)));

        cells.add(row1);
        cells.add(row2);

        assertTrue(maze.CheckTargetState(cells));
    }

    @Test
    void testCheckTargetState_ExitOutOfBounds() {
        List<Cell> row = new ArrayList<>();
        row.add(new Cell(false, new Direction(UP), null)); // Выход за границы вверх

        cells.add(row);

        assertFalse(maze.CheckTargetState(cells));
    }

    @Test
    void testCellMoved_ValidPath() {
        List<Cell> row = new ArrayList<>();
        row.add(new Cell(false, new Direction(UP), new Direction(DOWN)));

        cells.add(row);

        // Проверяем, что метод не бросает исключений
        assertDoesNotThrow(() -> maze.CellMoved(cells));
    }

    @Test
    void testSubscriberNotification() {
        TestSubscriber subscriber = new TestSubscriber();
        maze.AddSubscribers(subscriber);

        List<Cell> row = new ArrayList<>();
        row.add(new Cell(false, new Direction(UP), new Direction(DOWN)));
        cells.add(row);

        maze.CheckTargetState(cells);
        assertTrue(subscriber.wasNotified);
    }

    @Test
    void testAddAndRemoveSubscribers() {
        TestSubscriber subscriber1 = new TestSubscriber();
        TestSubscriber subscriber2 = new TestSubscriber();

        maze.AddSubscribers(subscriber1);
        maze.AddSubscribers(subscriber2);
        maze.RemoveSubscribers(subscriber2);

        List<Cell> row = new ArrayList<>();
        row.add(new Cell(false, new Direction(UP), new Direction(DOWN)));
        cells.add(row);

        maze.CheckTargetState(cells);
        assertTrue(subscriber1.wasNotified);
        assertFalse(subscriber2.wasNotified);
    }
}
