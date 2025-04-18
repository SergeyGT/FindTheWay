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

        directionEnter = new Direction();
        directionExit = new Direction();

        directionEnter.setDirection(DirectionEnum.DOWN);
        directionExit.setDirection(DirectionEnum.UP);
    }

    @Test
    void testCellInitialization() {
        Cell cell = cellFactory.createCell(2,3,true, directionEnter, directionExit);

        assertEquals(2, cell.getPosX());
        assertEquals(3, cell.getPosY());
        assertTrue(cell.getIsEmpty());
    }

    @Test
    void testGetPosition() {
        Cell cell = cellFactory.createCell(1, 1, false, directionEnter, directionExit);
        int[] pos = cell.getPosition();

        assertArrayEquals(new int[]{1, 1}, pos);
    }

    @Test
    void testSetPosition() {
        Cell cell = cellFactory.createCell(0, 0, false, directionEnter, directionExit);
        cell.setPosition(4, 5);

        assertEquals(4, cell.getPosX());
        assertEquals(5, cell.getPosY());
    }

    @Test
    void testIsEmptyFalse() {
        Cell cell = cellFactory.createCell(0, 0, false, directionEnter, directionExit);
        assertFalse(cell.getIsEmpty());
    }

    @Test
    void testIsEmptyTrue() {
        Cell cell = cellFactory.createCell(0, 0, true, directionEnter, directionExit);
        assertTrue(cell.getIsEmpty());
    }
}
