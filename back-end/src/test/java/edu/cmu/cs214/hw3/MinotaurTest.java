package edu.cmu.cs214.hw3;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class MinotaurTest {
    private Minotaur godcard;
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
        godcard = new Minotaur(game);
        game.initializePlayer1Workers(0, 0, 1, 1, godcard);
        game.initializePlayer2Workers(2, 3, 3, 3, godcard);
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
        assertTrue(godcard.isValidMove(worker, 1, 1));
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
        assertTrue(game.getBoard().getCell(2, 2).getIsOccupiedByPlayer());
    }

    @Test
    public void build() {
        godcard.build(worker, 1, 0);
        Cell cell = game.getBoard().getCell(0, 0);
        assertTrue(cell.getIsOccupiedByPlayer());
        assertTrue(game.getBoard().getCell(1, 0).getLevel() == 1);
    }
    
}