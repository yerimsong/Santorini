package edu.cmu.cs214.hw3;

public class Player {
    private Worker worker1;
    private Worker worker2;
    private Cell cell1;
    private Cell cell2;
    private GodCard godCard;

    /**
     * Constructor for Player class
     * @param x1 The x {@code int} coordinate for Worker 1
     * @param y1 The y {@code int} coordinate for Worker 1
     * @param x2 The x {@code int} coordinate for Worker 2
     * @param y2 The y {@code int} coordinate for Worker 2
     * @param cell1 The cell {@code Cell} for Worker 1
     * @param cell2 The cell {@code Cell} for Worker 2
     * @param godcard The godcard {@code GodCard} for Player
     * @param player The player {@code String}
     */
    public Player(int x1, int y1, int x2, int y2, Cell cell1, Cell cell2, GodCard godCard, String player) {
        this.worker1 = new Worker(x1, y1, cell1, player);
        this.worker2 = new Worker(x2, y2, cell2, player);
        this.cell1 = cell1;
        this.cell2 = cell2;
        this.godCard = godCard;
        cell1.setWorker(worker1);
        cell2.setWorker(worker2);
    }

    /**
     * Returns the player's Worker 1
     * @return {@link Worker} The current player's Worker 1
     */
    public Worker getWorker1() {
        return this.worker1;
    }

    /**
     * Returns the player's Worker 2
     * @return {@link Worker} The current player's Worker 2
     */
    public Worker getWorker2() {
        return this.worker2;
    }

    /**
     * Returns worker 1's tile
     * @return {@link Tile} of worker 1
     */
    public Cell getWorker1Tile() {
        return cell1;
    }

    /**
     * Returns worker 2's tile
     * @return {@link Tile} of worker 1
     */
    public Cell getWorker2Tile() {
        return cell2;
    }

    /**
     * Returns the player's God card
     * @return {@link godCard} The current player's god card
     */
    public GodCard getGodCard() {
        return godCard;
    }

}
