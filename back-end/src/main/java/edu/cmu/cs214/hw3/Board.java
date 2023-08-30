package edu.cmu.cs214.hw3;

public class Board {
    private final Cell[] cells;

    public Board() {
        cells = new Cell[25];
        for (int i = 0; i < 25; i++) {
            cells[i] = new Cell("", i/5, i%5);
        }
    }

    public Board(Cell[] cells) {
        this.cells = cells;
    }

    public Cell getCell(int x, int y) {
        return this.cells[x * 5 + y];
    }

    /**
     * Returns the board
     * @return {@link Tile[][]} board
     */
    public Cell[] getBoard() {
        return cells;
    }

    /**
     * Returns the Tile status at the given location
     * @param x The x coordinate of the location
     * @param y The y coordinate of the location
     * @return {@code true} if the Tile at the location isOccupied
     */
    public boolean getCellStatus(int x, int y) {
        return this.getCell(x, y).isOccupied();
    }

    /**
     * Returns the Tile level at the given location
     * @param x The x coordinate of the location
     * @param y The y coordinate of the location
     * @return {@code int} the build level at (x, y)
     */
    public int getCellLevel(int x, int y) {
        return this.getCell(x, y).getLevel();
    }

    /**
     * Sets the status of the Tile given the tile location and occupied status
     * @param x The x coordinate of the location
     * @param y The y coordinate of the location
     * @param occupied Whether the tile is occupied or not
     */
    public void setCellStatus_OccupiedByPlayer(int x, int y, boolean occupied) {
        this.getCell(x, y).setIsOccupiedByPlayer(occupied);
    }

    /**
     * Sets the status of the Tile given the tile location and occupied status
     * @param x The x coordinate of the location
     * @param y The y coordinate of the location
     * @param occupied Whether the tile is occupied or not
     */
    public void setCellStatus_OccupiedByDome(int x, int y, boolean occupied) {
        this.getCell(x, y).setIsOccupiedByDome(occupied);
    }

    /**
     * Increments the Tile at the location by 1
     * @param x The x coordinate of the location
     * @param y The y coordinate of the location
     */
    public void increaseCellLevel(int x, int y) {
        this.getCell(x, y).increaseLevel();
    }

}
