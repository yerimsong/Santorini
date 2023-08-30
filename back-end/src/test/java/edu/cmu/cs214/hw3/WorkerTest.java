package edu.cmu.cs214.hw3;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class WorkerTest {
    private Worker worker;
    private Cell cell;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        cell = new Cell("", 2, 2);
        worker = new Worker(2, 2, cell, "Player 1");
    }

    @Test
    public void getXTest() {
        assertTrue(worker.getX() == 2);
    }

    @Test
    public void getYTest() {
        assertTrue(worker.getY() == 2);
    }

    @Test
    public void setXTest() {
        worker.setX(0);
        assertTrue(worker.getX() == 0);
    }

    @Test
    public void setYTest() {
        worker.setY(0);
        assertTrue(worker.getY() == 0);
    }

    @Test
    public void getCellTest() {
        Cell new_cell = new Cell("", 0, 0);
        worker.setCell(new_cell);
        assertTrue(worker.getX() == 0);
        assertTrue(worker.getY() == 0);
    }
}
