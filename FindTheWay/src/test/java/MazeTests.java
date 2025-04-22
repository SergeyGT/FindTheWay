import Factories.CellFactory;
import Interfaces.IMaze;
import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.example.DirectionEnum.*;

public class MazeTests {

    private Maze maze;
    private CellFactory cellFactory;
    private List<List<Cell>> cells;

    // Заглушка для подписчика
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
        cellFactory = new CellFactory();
        cells = new ArrayList<>();
    }

    @Test
    void testCheckTargetState_ValidPath() {
        List<Cell> row = new ArrayList<>();
        row.add(cellFactory.createCell(0, 0, false, true, false, null, new Direction(RIGHT)));
        row.add(cellFactory.createCell(1, 0, false, false, true, new Direction(LEFT), null));

        cells.add(row);

        assertTrue(maze.CheckTargetState(cells));
    }

    @Test
    void testCheckTargetState_InvalidPath() {
        List<Cell> row = new ArrayList<>();
        row.add(cellFactory.createCell(0, 0, false, true, false, null, new Direction(UP)));
        row.add(cellFactory.createCell(1, 0, false, false, true, new Direction(RIGHT), null));

        cells.add(row);

        assertFalse(maze.CheckTargetState(cells));
    }

    @Test
    void testCheckTargetState_WithEmptyCells() {
//        List<Cell> row = new ArrayList<>();
//        row.add(cellFactory.createCell(0, 0, true, false, false, null, null));
//        row.add(cellFactory.createCell(1, 0, false, true, true, new Direction(LEFT), new Direction(RIGHT)));
//
//        cells.add(row);
//
//        assertFalse(maze.CheckTargetState(cells)); // Нет связи между ячейками
        List<Cell> row1 = new ArrayList<>();
        row1.add(cellFactory.createCell(0, 0, true, false, false, null, null)); // Пустая ячейка
        row1.add(cellFactory.createCell(1, 0, false, true, false, null, null)); // Ячейка стартовая, но без выхода

        List<Cell> row2 = new ArrayList<>();
        row2.add(cellFactory.createCell(0, 1, true, false, false, null, null)); // Пустая ячейка
        row2.add(cellFactory.createCell(1, 1, false, false, true, new Direction(LEFT), new Direction(RIGHT))); // Конечная ячейка

        cells.add(row1);
        cells.add(row2);

        assertFalse(maze.CheckTargetState(cells)); // Нет пути, так как ячейки не соединены
    }

    @Test
    void testCheckTargetState_ExitOutOfBounds() {
        List<Cell> row = new ArrayList<>();
        row.add(cellFactory.createCell(0, 0, false, true, false, null, new Direction(UP))); // Вверх – за пределы

        cells.add(row);

        assertFalse(maze.CheckTargetState(cells));
    }

    @Test
    void testCellMoved_ValidPath() {
        List<Cell> row = new ArrayList<>();
        row.add(cellFactory.createCell(0, 0, false, true, false, null, new Direction(RIGHT)));
        row.add(cellFactory.createCell(1, 0, false, false, true, new Direction(LEFT), null));

        cells.add(row);

        assertDoesNotThrow(() -> maze.CellMoved(cells));
    }

    @Test
    void testSubscriberNotification() {
        TestSubscriber subscriber = new TestSubscriber();
        maze.AddSubscribers(subscriber);

        List<Cell> row = new ArrayList<>();
        row.add(cellFactory.createCell(0, 0, false, true, false, null, new Direction(RIGHT)));
        row.add(cellFactory.createCell(1, 0, false, false, true, new Direction(LEFT), null));

        cells.add(row);

        maze.CellMoved(cells);
        assertTrue(subscriber.wasNotified);
    }

    @Test
    void testAddAndRemoveSubscribers() {
        TestSubscriber sub1 = new TestSubscriber();
        TestSubscriber sub2 = new TestSubscriber();

        maze.AddSubscribers(sub1);
        maze.AddSubscribers(sub2);
        maze.RemoveSubscribers(sub2);

        List<Cell> row = new ArrayList<>();
        row.add(cellFactory.createCell(0, 0, false, true, false, null, new Direction(RIGHT)));
        row.add(cellFactory.createCell(1, 0, false, false, true, new Direction(LEFT), null));

        cells.add(row);

        maze.CellMoved(cells);
        assertTrue(sub1.wasNotified);
        assertFalse(sub2.wasNotified);
    }
}
