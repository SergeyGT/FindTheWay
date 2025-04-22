package org.example;

import Factories.CellFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellsTest {
    CellFactory cellFactory;
    Direction directionEnter;
    Direction directionExit;

    @BeforeEach
    void setUp(){
        cellFactory = new CellFactory();

        directionEnter = new Direction(DirectionEnum.DOWN);
        directionExit = new Direction(DirectionEnum.UP);

    }

    @Test
    void testCellInitialization() {
        Cell cell = cellFactory.createCell(2, 3, true, false, true, directionEnter, directionExit);

        assertEquals(2, cell.getPosX());
        assertEquals(3, cell.getPosY());
        assertTrue(cell.getIsEmpty());
        assertFalse(cell.isStart());
        assertTrue(cell.isEnd());
        assertEquals(DirectionEnum.DOWN, cell.getDirectionEnter().getDirectionEnum());
        assertEquals(DirectionEnum.UP, cell.getDirectionExit().getDirectionEnum());
    }

    @Test
    void testGetPosition() {
        Cell cell = cellFactory.createCell(1, 1, false, true, false, directionEnter, directionExit);
        int[] pos = cell.getPosition();

        assertArrayEquals(new int[]{1, 1}, pos);
    }

    @Test
    void testSetPosition() {
        Cell cell = cellFactory.createCell(0, 0, false, false, false, directionEnter, directionExit);
        cell.setPosition(4, 5);

        assertEquals(4, cell.getPosX());
        assertEquals(5, cell.getPosY());
    }

    @Test
    void testIsEmptyFalse() {
        Cell cell = cellFactory.createCell(0, 0, false, false, false, directionEnter, directionExit);
        assertFalse(cell.getIsEmpty());
    }

    @Test
    void testIsEmptyTrue() {
        Cell cell = cellFactory.createCell(0, 0, true, false, false, directionEnter, directionExit);
        assertTrue(cell.getIsEmpty());
    }

    @Test
    void testIsStartAndIsEnd() {
        Cell startCell = cellFactory.createCell(0, 0, false, true, false, directionEnter, directionExit);
        Cell endCell = cellFactory.createCell(1, 0, false, false, true, directionEnter, directionExit);

        assertTrue(startCell.isStart());
        assertFalse(startCell.isEnd());

        assertFalse(endCell.isStart());
        assertTrue(endCell.isEnd());
    }
}
