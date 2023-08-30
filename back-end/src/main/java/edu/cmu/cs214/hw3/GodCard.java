package edu.cmu.cs214.hw3;

public interface GodCard {
    /*
     * Checks if the god card move is valid
     * @returns {@code true} if the move is valid
     */
    public boolean isValidMove(Worker worker, int new_x, int new_y);

    /*
     * Checks if the god card build is valid
     * @returns {@code true} if the build is valid
     */
    public boolean isValidBuild(Worker worker, int new_x, int new_y);

    /*
     * Moves the provided worker to the given location
     */
    public void move(Worker worker, int new_x, int new_y);

    /*
     * Builds the provided worker to the given location
     */
    public void build(Worker worker, int new_x, int new_y);

}
