package edu.cmu.cs214.hw3;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;
    private Worker worker1;
    private Worker worker2;
    private Cell cell1;
    private Cell cell2;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        cell1 = new Cell("", 0, 0);
        cell2 = new Cell("", 2, 2);
        player = new Player(0, 0, 2, 2, cell1, cell2, new Demeter(new Game()), "Player 1");
        worker1 = player.getWorker1();
        worker2 = player.getWorker2();
    }

    @Test
    public void getWorker1() {
        assertTrue(worker1.getX() == 0);
        assertTrue(worker1.getY() == 0);
    }

    @Test
    public void getWorker2() {
        assertTrue(worker2.getX() == 2);
        assertTrue(worker2.getY() == 2);
    }

    @Test
    public void getWorker1Cell() {
        assertTrue(worker1.getCell() == cell1);
    }

    @Test
    public void getWorker2Cell() {
        assertTrue(worker2.getCell() == cell2);
    }
}
