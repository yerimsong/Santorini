package edu.cmu.cs214.hw3;

public class Cell {
    private boolean occupiedByPlayer;
    private boolean occupiedByDome;
    private int level;
    private Worker worker;
    private String text;
    private int x;
    private int y;

    /**
     * Constructor for Tile
     */
    public Cell(String text, int x, int y) {
        occupiedByPlayer = false;
        occupiedByDome = false;
        level = 0;
        worker = null;
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Returns the current status of the Tile
     * @return {@code true} if the Tile is occupied by Player
     */
    public boolean getIsOccupiedByPlayer() {
        return occupiedByPlayer;
    }

    /**
     * Returns the current status of the Tile
     * @return {@code true} if the Tile is occupied by Dome
     */
    public boolean getIsOccupiedByDome() {
        return occupiedByDome;
    }

    /**
     * Returns the current status of the Tile
     * @return {@code true} if the Tile is occupied by Dome
     */
    public boolean isOccupied() {
        return occupiedByDome || occupiedByPlayer;
    }

    /**
     * Returns the current level of the Tile
     * @return {@code int} The build level of the tile
     */
    public int getLevel() {
        return level;
    }

    public Worker getWorker() {
        return worker;
    }

    /**
     * Sets the occupied status of the Tile
     * @param b {@code true} if the Tile is occupied by Player
     */
    public void setIsOccupiedByPlayer(boolean b) {
        occupiedByPlayer = b;
    }

    /**
     * Sets the status of the Tile
     * @param b {@code true} if the Tile is occupied by Dome
     */
    public void setIsOccupiedByDome(boolean b) {
        occupiedByDome = b;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    /**
     * Increments the current tile level by 1
     */
    public void increaseLevel() {
        level++;
    }

    @Override
    public String toString() {
        return "{ \"text\": \"" + this.text + "\"," +
                " \"level\": " + this.level + " }" ;
    }
}
