package edu.cmu.cs214.hw3;

import java.util.Arrays;

public class GameState {

    private final Cell[] cells;
    private final boolean hasWinner;
    private final boolean isPlayer1Turn;

    private GameState(Cell[] cells, boolean winner, boolean isPlayer1Turn) {
        this.cells = cells;
        this.hasWinner = winner;
        this.isPlayer1Turn = isPlayer1Turn;
    }

    public static GameState forGame(Game game) {
        Cell[] cells = getCells(game);
        return new GameState(cells, game.getIsGameOver(), game.getIsPlayer1Turn());
    }

    public Cell[] getCells() {
        return this.cells;
    }

    public int getTurn() {
        if (isPlayer1Turn) return 1;
        else return 2;
    }

    /**
     * toString() of GameState will return the string representing
     * the GameState in JSON format.
     */
    @Override
    public String toString() {
        if (!this.hasWinner) {
            return "{ \"cells\": " + Arrays.toString(this.cells) + "," +
                     "\"player\": " + this.getTurn() + "," +
                     "\"winner\": " + null + "}";
        } else {
            return "{ \"cells\": " + Arrays.toString(this.cells) + "," +
                     "\"player\": " + this.getTurn() + "," +
                     "\"winner\": " + this.getTurn() + "}";
        }
    }

    public String generateJSONString(boolean haserror, boolean contBuild) {
        if (!this.hasWinner) {
            return "{ \"cells\": " + Arrays.toString(this.cells) + "," +
                        "\"player\": " + this.getTurn() + "," +
                        "\"haserror\": " + haserror + "," +
                        "\"continueBuild\": " + contBuild + "," +
                        "\"winner\": " + null + "}";
        } else {
            return "{ \"cells\": " + Arrays.toString(this.cells) + "," +
                        "\"player\": " + this.getTurn() + "," +
                        "\"haserror\": " + haserror + "," +
                        "\"continueBuild\": " + contBuild + "," +
                        "\"winner\": " + this.getTurn() + "}";
        }    
    }

    private static Cell[] getCells(Game game) {
        Cell cells[] = new Cell[25];
        Board board = game.getBoard();
        for (int x = 0; x <= 4; x++) {
            for (int y = 0; y <= 4; y++) {
                Cell cell = board.getCell(x, y);
                int level = cell.getLevel();
                String text = Integer.toString(level);
                Worker worker = cell.getWorker();
                if (cell.getIsOccupiedByDome()) {
                    text = "Dome";
                }
                if (worker != null) {
                    text += " -> " + worker.getPlayer();
                    if (game.getCurrentWorker() == worker) {
                        text += " (selected cell) ";
                    }
                }
                if (game.getIsGameOver()) {
                    text = "";
                }
                cells[5 * x + y] = new Cell(text, x, y);
            }
        }
        return cells;
    }
}