package org.example;

import Factories.CellFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellsTest {
    CellFactory cellFactory;

    @BeforeEach
    void setUp(){
        cellFactory = new CellFactory();
    }

    @Test
    void testCellInitialization() {
        Cell cell = cellFactory.createCell(2,3,true);

        assertEquals(2, cell.getPosX());
        assertEquals(3, cell.getPosY());
        assertTrue(cell.getIsEmpty());
    }

    @Test
    void testGetPosition() {
        Cell cell = cellFactory.createCell(1, 1, false);
        int[] pos = cell.getPosition();

        assertArrayEquals(new int[]{1, 1}, pos);
    }

    @Test
    void testSetPosition() {
        Cell cell = cellFactory.createCell(0, 0, false);
        cell.setPosition(4, 5);

        assertEquals(4, cell.getPosX());
        assertEquals(5, cell.getPosY());
    }

    @Test
    void testIsEmptyFalse() {
        Cell cell = cellFactory.createCell(0, 0, false);
        assertFalse(cell.getIsEmpty());
    }

    @Test
    void testIsEmptyTrue() {
        Cell cell = cellFactory.createCell(0, 0, true);
        assertTrue(cell.getIsEmpty());
    }
}
