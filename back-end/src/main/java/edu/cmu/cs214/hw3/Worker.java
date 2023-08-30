package edu.cmu.cs214.hw3;

public class Worker {
    private int x;
    private int y;
    private Cell cell;
    private String player;
    
    /**
     * Constructor for Worker class
     * @param x The x {@code int} coordinate for the worker
     * @param y The y {@code int} coordinate for the worker
     * @param cell The cell {@code Cell} the worker is at
     * @param player The player {@code String} corresponding to the worker
     */
    public Worker(int x, int y, Cell cell, String player) {
        this.x = x;
        this.y = y;
        this.cell = cell;
        this.player = player;
    }

    /**
     * Returns the curent x coordinate of the worker
     * @return The x {@code int} coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the curent y coordinate of the worker
     * @return The y {@code int} coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Returns the current Tile the worker is at
     * @return The {@linke Tile} the worker is at
     */
    public Cell getCell() {
        return this.cell;
    }

     /**
     * Gets the worker's corresponding player in String form
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Moves the worker to the provided x coordinate
     */
    public void setX(int new_x) {
        this.x = new_x;
    }

    /**
     * Moves the worker to the provided y coordinate
     */
    public void setY(int new_y) {
        this.y = new_y;
    }

     /**
     * Sets the worker's cell to the provided cell
     */
    public void setCell(Cell cell) {
        this.x = cell.getX();
        this.y = cell.getY();
        this.cell = cell;
    }

}
