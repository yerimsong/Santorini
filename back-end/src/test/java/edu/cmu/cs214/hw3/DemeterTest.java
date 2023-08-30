package edu.cmu.cs214.hw3;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class DemeterTest {
    private Demeter godcard;
    Worker worker;
    Game game;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        game = new Game();
        godcard = new Demeter(game);
        game.initializePlayer1Workers(0, 0, 1, 1, godcard);
        game.initializePlayer2Workers(2, 2, 3, 3, godcard);
        worker = game.getPlayer1().getWorker1();
    }

    @Test
    public void isValidMove_Test1() {
        assertTrue(godcard.isValidMove(worker, 1, 0));
    }

    @Test
    public void isValidMove_Test2() {
        assertTrue(godcard.isValidMove(worker, 0, 1));
    }

    @Test
    public void isValidMove_Test3() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Index out of bounds on board");
        godcard.isValidMove(worker, -1, 0);
    }

    @Test
    public void isValidMove_Test4() {
        assertFalse(godcard.isValidMove(worker, 2, 0));
    }

    @Test
    public void isValidMove_Test5() {
        assertFalse(godcard.isValidMove(worker, 1, 1));
    }

    @Test
    public void isValidBuild_Test1() {
        assertTrue(godcard.isValidBuild(worker, 1, 0));
    }

    @Test
    public void isValidBuild_Test2() {
        assertFalse(godcard.isValidBuild(worker, 1, 1));
    }

    @Test
    public void isValidBuild_Test3() {
        game.getBoard().getCell(1, 0).increaseLevel();
        game.getBoard().getCell(1, 0).increaseLevel();
        assertTrue(godcard.isValidBuild(worker, 1, 0));
    }

    @Test
    public void move_Test1() {
        godcard.move(worker, 1, 0);
        Cell cell = game.getBoard().getCell(0, 0);
        assertTrue(worker.getCell().getIsOccupiedByPlayer());
        assertFalse(cell.getIsOccupiedByPlayer());
    }

    @Test
    public void move_Test2() {
        godcard.move(worker, 1, 1);
        Cell cell = game.getBoard().getCell(0, 0);
        assertTrue(worker.getCell().getIsOccupiedByPlayer());
        assertFalse(cell.getIsOccupiedByPlayer());
    }

    @Test
    public void build_Test1() {
        godcard.build(worker, 1, 0);
        Cell cell = game.getBoard().getCell(0, 0);
        assertTrue(cell.getIsOccupiedByPlayer());
        assertTrue(game.getBoard().getCell(1, 0).getLevel() == 1);
    }

    @Test
    public void play_Test1() {
        game.setWorker(worker);
        game.attemptMovePlayer1Worker(worker, 1, 0);
        assertTrue(game.getBoard().getCell(1, 0).getIsOccupiedByPlayer());
        assertFalse(game.getBoard().getCell(0, 0).getIsOccupiedByPlayer());
        game.attemptBuildPlayer1Worker(worker, 0, 0);
        assertTrue(game.getContinueBuild());
        game.attemptBuildPlayer1Worker(worker, 0, 1);
        assertFalse(game.getContinueBuild());
        assertTrue(game.getBoard().getCell(1, 0).getLevel() == 0);
        assertTrue(game.getBoard().getCell(0, 0).getLevel() == 1);
        assertTrue(game.getBoard().getCell(0, 1).getLevel() == 1);
    }

    @Test
    public void play_Test2() {
        game.setWorker(worker);
        game.attemptMovePlayer1Worker(worker, 1, 0);
        assertTrue(game.getBoard().getCell(1, 0).getIsOccupiedByPlayer());
        assertFalse(game.getBoard().getCell(0, 0).getIsOccupiedByPlayer());
        game.attemptBuildPlayer1Worker(worker, 0, 0);
        assertTrue(game.getContinueBuild());
        game.attemptBuildPlayer1Worker(worker, 1, 0);
        assertFalse(game.getContinueBuild());
        assertTrue(game.getBoard().getCell(1, 0).getLevel() == 0);
        assertTrue(game.getBoard().getCell(0, 0).getLevel() == 1);
        assertTrue(game.getBoard().getCell(0, 1).getLevel() == 0);
    }

    @Test
    public void play_Test3() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid build");
        game.setWorker(worker);
        game.attemptMovePlayer1Worker(worker, 1, 0);
        assertTrue(game.getBoard().getCell(1, 0).getIsOccupiedByPlayer());
        assertFalse(game.getBoard().getCell(0, 0).getIsOccupiedByPlayer());
        game.attemptBuildPlayer1Worker(worker, 1, 0);
    }

    @Test
    public void play_Test4() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid build");
        game.setWorker(worker);
        game.attemptMovePlayer1Worker(worker, 1, 0);
        assertTrue(game.getBoard().getCell(1, 0).getIsOccupiedByPlayer());
        assertFalse(game.getBoard().getCell(0, 0).getIsOccupiedByPlayer());
        game.attemptBuildPlayer1Worker(worker, 0, 0);
        assertTrue(game.getContinueBuild());
        game.attemptBuildPlayer1Worker(worker, 3, 0);
    }
    
}