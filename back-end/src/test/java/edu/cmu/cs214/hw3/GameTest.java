package edu.cmu.cs214.hw3;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class GameTest {
    private Game game;
    private Player player1;
    private Player player2;
    private Worker worker1P1;
    private Worker worker2P1;
    private Worker worker1P2;
    private Worker worker2P2;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        game = new Game();
        game.initializePlayer1Workers(0, 0, 2, 2, new NoGodCard(game));
        game.initializePlayer2Workers(1, 0, 3, 1, new NoGodCard(game));
        player1 = game.getPlayer1();
        player2 = game.getPlayer2();
        worker1P1 = player1.getWorker1();
        worker2P1 = player1.getWorker2();
        worker1P2 = player2.getWorker1();
        worker2P2 = player2.getWorker2();
    }

    @Test
    public void checkPlayer1Worker1() {
        assertTrue(player1.getWorker1().getX() == 0);
        assertTrue(player1.getWorker1().getY() == 0);
        assertTrue(game.getBoard().getCellStatus(0, 0));
    }

    @Test
    public void checkPlayer1Worker2() {
        assertTrue(player1.getWorker2().getX() == 2);
        assertTrue(player1.getWorker2().getY() == 2);
        assertTrue(game.getBoard().getCellStatus(2, 2));
    }

    @Test
    public void checkPlayer2Worker1() {
        assertTrue(player2.getWorker1().getX() == 1);
        assertTrue(player2.getWorker1().getY() == 0);
        assertTrue(game.getBoard().getCellStatus(1, 0));
    }
    
    @Test
    public void checkPlayer2Worker2() {
        assertTrue(player2.getWorker2().getX() == 3);
        assertTrue(player2.getWorker2().getY() == 1);
        assertTrue(game.getBoard().getCellStatus(3, 1));
    }

    @Test
    // valid space to move into
    public void movePlayer1_Test1Success() {
        game.attemptMovePlayer1Worker(worker1P1, 0, 1);
        Worker worker = player1.getWorker1();
        assertFalse(game.getBoard().getCellStatus(0, 0));
        assertTrue(game.getBoard().getCellStatus(0, 1));
        assertTrue(worker.getX() == 0);
        assertTrue(worker.getY() == 1);
    }

    @Test
    // occupied space will fail
    public void movePlayer1_Test2Failure() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid move");
        game.attemptMovePlayer1Worker(worker1P1, 1, 0);
    }

    @Test
    public void movePlayer1_Test3Failure() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid move");
        game.attemptMovePlayer1Worker(worker1P1, 0, 2);
    }

    @Test
    public void movePlayer1_Test4Success() {
        game.attemptMovePlayer1Worker(worker2P1, 1, 1);
        Worker worker = player1.getWorker2();
        assertFalse(game.getBoard().getCellStatus(2, 2));
        assertTrue(game.getBoard().getCellStatus(1, 1));
        assertTrue(worker.getX() == 1);
        assertTrue(worker.getY() == 1);
    }

    @Test
    public void movePlayer1_Test5Success() {
        game.attemptMovePlayer1Worker(worker2P1, 3, 2);
        Worker worker = player1.getWorker2();
        assertFalse(game.getBoard().getCellStatus(2, 2));
        assertTrue(game.getBoard().getCellStatus(3, 2));
        assertTrue(worker.getX() == 3);
        assertTrue(worker.getY() == 2);
    }

    @Test
    public void movePlayer2_Test1Success() {
        game.setIsPlayer1Turn(false);
        game.attemptMovePlayer2Worker(worker1P2, 1, 1);
        Worker worker = player2.getWorker1();
        assertFalse(game.getBoard().getCellStatus(1, 0));
        assertTrue(game.getBoard().getCellStatus(1, 1));
        assertTrue(worker.getX() == 1);
        assertTrue(worker.getY() == 1);
    }

    @Test
    public void movePlayer2_Test2Failure() {
        game.setIsPlayer1Turn(false);
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid move");
        game.attemptMovePlayer2Worker(worker1P2, 0, 0);
    }

    @Test
    public void movePlayer2_Test3Failure() {
        game.setIsPlayer1Turn(false);
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid move");
        game.attemptMovePlayer2Worker(worker1P2, 1, -1);
    }

    @Test
    public void movePlayer2_Test4Failure() {
        game.setIsPlayer1Turn(false);
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid move");
        game.attemptMovePlayer2Worker(worker1P2, 1, 5);
    }

    @Test
    public void movePlayer2_Test7Failure() {
        game.setIsPlayer1Turn(false);
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid move");
        assertTrue(game.getBoard().getCellLevel(1, 0) == 0);
        game.getBoard().increaseCellLevel(1, 1);
        game.getBoard().increaseCellLevel(1, 1);
        game.attemptMovePlayer2Worker(worker1P2, 1, 1);
    }

    @Test
    public void movePlayer2_Test8Failure() {
        game.setIsPlayer1Turn(false);
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid move");
        game.getBoard().increaseCellLevel(2, 1);
        game.getBoard().increaseCellLevel(2, 1);
        game.attemptMovePlayer2Worker(worker2P2, 2, 1);
    }

    @Test
    public void movePlayer2_Test8Success() {
        game.setIsPlayer1Turn(false);
        game.getBoard().increaseCellLevel(2, 1);
        game.attemptMovePlayer2Worker(worker2P2, 2, 1);
        assertTrue(game.getBoard().getCellLevel(2, 1) == 1);
        assertTrue(game.getBoard().getCellStatus(2, 1));
        assertFalse(game.getBoard().getCellStatus(3, 1));
    }

    @Test
    public void buildPlayer1_Test1Success() {
        game.setWorker(worker1P1);
        game.attemptMovePlayer1Worker(worker1P1, 1, 1);
        game.attemptBuildPlayer1Worker(worker1P1, 0, 1);
        Worker worker = player1.getWorker1();
        assertFalse(game.getBoard().getCellStatus(0, 0));
        assertTrue(game.getBoard().getCellLevel(0, 0) == 0);
        assertTrue(game.getBoard().getCellLevel(0, 1) == 1);
        assertTrue(worker.getX() == 1);
        assertTrue(worker.getY() == 1);
    }

    @Test
    public void buildPlayer1_Test2Fail() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Bad Game status");
        game.attemptMovePlayer1Worker(worker1P1, 1, 1);
        game.attemptBuildPlayer1Worker(worker1P1, 1, 1);
    }

    @Test
    public void buildPlayer1_Test3Fail() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Bad Game status");
        game.attemptMovePlayer1Worker(worker1P1, 1, 1);
        game.attemptBuildPlayer1Worker(worker1P1, 1, 0);
    }

    @Test
    public void buildPlayer1_Test4Fail() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Bad Game status");
        game.attemptMovePlayer1Worker(worker1P1, 0, 1);
        game.attemptBuildPlayer1Worker(worker1P1, -1, 1);
    }

    @Test
    public void buildPlayer1_Test5Success() {
        game.setWorker(worker2P1);
        game.attemptMovePlayer1Worker(worker2P1, 2, 1);
        game.attemptBuildPlayer1Worker(worker2P1, 1, 1);
        Worker worker = player1.getWorker2();
        assertTrue(game.getBoard().getCellStatus(0, 0));
        assertTrue(game.getBoard().getCellLevel(1, 1) == 1);
        assertTrue(game.getBoard().getCellLevel(2, 2) == 0);
        assertTrue(worker.getX() == 2);
        assertTrue(worker.getY() == 1);
    }

    @Test
    public void buildPlayer1_Test6Success() {
        game.setWorker(worker2P1);
        game.attemptMovePlayer1Worker(worker2P1, 1, 2);
        game.attemptBuildPlayer1Worker(worker2P1, 1, 1);
        Worker worker = player1.getWorker2();
        assertTrue(game.getBoard().getCellStatus(0, 0));
        assertTrue(game.getBoard().getCellLevel(1, 1) == 1);
        assertTrue(game.getBoard().getCellLevel(2, 2) == 0);
        assertTrue(worker.getX() == 1);
        assertTrue(worker.getY() == 2);
    }


    // Game play tests

    @Test
    public void play1() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Bad Game status");
        game.attemptMovePlayer1Worker(worker1P1, 0, 1);
        game.attemptMovePlayer1Worker(worker1P1, 1, 1);
    }

    @Test
    public void play2() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Bad Game status");
        game.attemptMovePlayer1Worker(worker1P1, 0, 1);
        game.attemptBuildPlayer1Worker(worker2P1, 1, 1);
    }

    @Test
    public void play3() {
        game.setWorker(worker1P1);
        game.attemptMovePlayer1Worker(worker1P1, 0, 1);
        game.attemptBuildPlayer1Worker(worker1P1, 1, 1);
        assertTrue(worker1P1.getX() == 0);
        assertTrue(worker1P1.getY() == 1);
        assertTrue(worker1P1.getCell().getLevel() == 0);
        assertTrue(game.getBoard().getCellLevel(0, 1) == 0);
        assertTrue(game.getBoard().getCellLevel(1, 1) == 1);
    }

    @Test
    public void play4() {
        game.setWorker(worker1P1);
        game.attemptMovePlayer1Worker(worker1P1, 0, 1);
        game.attemptBuildPlayer1Worker(worker1P1, 1, 1);
        game.setWorker(worker1P2);
        game.attemptMovePlayer2Worker(worker1P2, 2, 0);
        game.attemptBuildPlayer2Worker(worker1P2, 3, 0);
        assertTrue(worker1P1.getX() == 0);
        assertTrue(worker1P1.getY() == 1);
        assertTrue(worker1P1.getCell().getLevel() == 0);
        assertTrue(game.getBoard().getCellLevel(0, 1) == 0);
        assertTrue(game.getBoard().getCellLevel(1, 1) == 1);
        assertTrue(game.getBoard().getCellLevel(2, 0) == 0);
        assertTrue(game.getBoard().getCellLevel(3, 0) == 1);
    }

    @Test
    public void play5() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Bad Game status");
        game.attemptMovePlayer1Worker(worker1P1, 0, 1);
        game.attemptBuildPlayer1Worker(worker1P1, 1, 1);
        game.attemptBuildPlayer2Worker(worker1P2, 3, 0);
    }

    @Test
    public void play6() {
        game.setWorker(worker1P1);
        game.attemptMovePlayer1Worker(worker1P1, 0, 1);
        game.attemptBuildPlayer1Worker(worker1P1, 1, 1);
        game.setWorker(worker1P2);
        game.attemptMovePlayer2Worker(worker1P2, 2, 0);
        game.attemptBuildPlayer2Worker(worker1P2, 3, 0);
        game.setWorker(worker1P1);
        game.attemptMovePlayer1Worker(worker1P1, 1, 1);
        assertTrue(worker1P1.getX() == 1);
        assertTrue(worker1P1.getY() == 1);
        assertTrue(game.getBoard().getCellLevel(1, 1) == 1);
    }
    
}
