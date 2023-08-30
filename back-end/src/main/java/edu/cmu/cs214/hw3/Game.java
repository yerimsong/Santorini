package edu.cmu.cs214.hw3;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    
    // Game states
    private boolean isPlayer1Turn;
    private boolean isBuildTurn;
    private Worker currWorker;
    private boolean isGameOver;
    private boolean isPlayer1Initialized;
    private boolean isPlayer2Initialized;
    private boolean continueBuild;

    /**
     * Constructor for Game class
     */
    public Game() {
        board = new Board();
        isPlayer1Turn = true;
        isBuildTurn = false;
        currWorker = null;
        isGameOver = false;
        isPlayer1Initialized = false;
        isPlayer2Initialized = false;
        continueBuild = false;
    }

    /**
     * Initializes both of Player 1's workers
     * @param x1 The x coordinate for Player 1's Worker 1
     * @param y1 The y coordinate for Player 1's Worker 1
     * @param x2 The x coordinate for Player 1's Worker 2
     * @param y2 The y coordinate for Player 1's Worker 2
     */
    public void initializePlayer1Workers(int x1, int y1, int x2, int y2, GodCard godCard) {
        if (isPlayer1Initialized || !checkInBounds(x1, y1) || !checkInBounds(x2, y2)) {
            throw new IllegalArgumentException("Bad initialization");
        }
        Cell cell1 = board.getCell(x1, y1);
        Cell cell2 = board.getCell(x2, y2);
        player1 = new Player(x1, y1, x2, y2, cell1, cell2, godCard, "Player 1");
        board.setCellStatus_OccupiedByPlayer(x1, y1, true);
        board.setCellStatus_OccupiedByPlayer(x2, y2, true);
        isPlayer1Initialized = true;
    }

    /**
     * Initializes both of Player 2's workers
     * @param x1 The x coordinate for Player 2's Worker 1
     * @param y1 The y coordinate for Player 2's Worker 1
     * @param x2 The x coordinate for Player 2's Worker 2
     * @param y2 The y coordinate for Player 2's Worker 2
     */
    public void initializePlayer2Workers(int x1, int y1, int x2, int y2, GodCard godCard) {
        if (isPlayer2Initialized || !checkInBounds(x1, y1) || !checkInBounds(x2, y2)) {
            throw new IllegalArgumentException("Bad initialization");
        }
        Cell cell1 = board.getCell(x1, y1);
        Cell cell2 = board.getCell(x2, y2);
        player2 = new Player(x1, y1, x2, y2, cell1, cell2, godCard, "Player 2");
        board.setCellStatus_OccupiedByPlayer(x1, y1, true);
        board.setCellStatus_OccupiedByPlayer(x2, y2, true);
        isPlayer2Initialized = true;
        if (!checkDuplicateCoordinates()) {
            throw new IllegalArgumentException("Duplicate coordinates");
        }
    }

    /**
     * Returns board, which marks the levels at each location
     * @return {@code int[][]} The current board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns worker that the game is playing with
     * @return {@code Worker} The current worker
     */
    public Worker getCurrentWorker() {
        return currWorker;
    }

    /**
     * Return Player 1
     * @return {@link Player} player 1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Return Player 2
     * @return {@link Player} player 2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Returns whether it is Player 1's turn
     * @return {@code true} if it Player 1's turn
     */
    public boolean getIsPlayer1Turn() {
        return isPlayer1Turn;
    }

    /**
     * Returns whether game is over
     * @return {@code true} if game is over
     */
    public boolean getIsGameOver() {
        return isGameOver;
    }

    /**
     * Returns if game is in build state
     * @return {@code boolean} The current build state
     */
    public boolean getContinueBuild() {
        return continueBuild;
    }

    /**
     * Sets the game to be initialized
     * @param {@code true} Whether game is set up or not
     */
    public void setIsSetUp(boolean b) {
        isPlayer1Initialized = true;
        isPlayer2Initialized = true;
        isGameOver = false;
    }

    /**
     * Sets the worker the game plays with
     * @param {@code Worker} The current worker
     */
    public void setWorker(Worker worker) {
        if ((worker.getPlayer().equals("Player 1") && isPlayer1Turn) 
            || (worker.getPlayer().equals("Player 2") && !isPlayer1Turn)) {
                currWorker = worker;
            }
        else {
            throw new IllegalArgumentException("Wrong player's worker");
        }
    }

    /**
     * Sets the turn to Player 1 or Player 2
     * @param {@code true} if it is Player 1's turn
     */
    public void setIsPlayer1Turn(boolean b) {
        isPlayer1Turn = b;
    }

    /**
     * Sets the game to be over
     * @param {@code true} if game is over
     */
    public void setIsGameOver(boolean b) {
        isGameOver = b;
    }

    /**
     * Sets the state to continue build
     * @param {@code true} if continue building
     */
    public void setContinueBuild(boolean b) {
        continueBuild = b;
    }

    /**
     * Checks if the game is initialized
     * @param {@code true} if game is initialized
     */
    public boolean isSetUp() {
        return isPlayer1Initialized && isPlayer2Initialized && !isGameOver;
    }

    /**
     * Checks if the initialized locations are duplicates
     * @param {@code true} if no duplicates
     */
    private boolean checkDuplicateCoordinates() {
        int x1 = player1.getWorker1().getX();
        int y1 = player1.getWorker1().getY();
        int x2 = player1.getWorker2().getX();
        int y2 = player1.getWorker2().getY();
        int x3 = player2.getWorker1().getX();
        int y3 = player2.getWorker1().getY();
        int x4 = player2.getWorker2().getX();
        int y4 = player2.getWorker2().getY();
        if (x1 == x2 && y1 == y2) {
            return false;
        }
        if (x2 == x3 && y2 == y3) {
            return false;
        }
        if (x3 == x4 && y3 == y4) {
            return false;
        }
        if (x1 == x4 && y1 == y4) {
            return false;
        }
        if (x1 == x3 && y1 == y3) {
            return false;
        }
        if (x2 == x4 && y2 == y4) {
            return false;
        }
        return true;
    }

    /**
     * Moves Player 1's worker to the new location provided and checks if Game has been won if
     * the current status is valid
     * @param worker The {@link Worker} that will move
     * @param new_x The x coordinate to move into
     * @param new_y The y coordinate to move into
     * @throws IllegalStateException if it is not Player 1's turn
     * @throws IllegalArgumentException if the move was invalid
     */
    public void attemptMovePlayer1Worker(Worker worker, int new_x, int new_y) {
        if (!isSetUp() || !isPlayer1Turn || isBuildTurn) throw new IllegalStateException("Bad Game status");
        
        GodCard godcard = player1.getGodCard();

        if (godcard.isValidMove(worker, new_x, new_y)) {
            godcard.move(worker, new_x, new_y);
        }
        else {
            throw new IllegalArgumentException("Invalid move");
        }

        if (worker.getCell().getLevel() == 3) {
            isGameOver = true;
        }

        isBuildTurn = true;
    }

    /**
     * Moves Player 2's worker to the new location provided and checks if Game has been won if
     * the current status is valid
     * @param worker The {@link Worker} that will move
     * @param new_x The x coordinate to move into
     * @param new_y The y coordinate to move into
     * @throws IllegalStateException if it is Player 1's turn
     * @throws IllegalArgumentException if the move was invalid
     */
    public void attemptMovePlayer2Worker(Worker worker, int new_x, int new_y) {
        if (!isSetUp() || isPlayer1Turn || isBuildTurn) throw new IllegalStateException("Bad Game status");

        GodCard godcard = player2.getGodCard();

        if (godcard.isValidMove(worker, new_x, new_y)) {
            godcard.move(worker, new_x, new_y);
        }
        else {
            throw new IllegalArgumentException("Invalid move");
        }

        if (worker.getCell().getLevel() == 3) {
            isGameOver = true;
        }

        isBuildTurn = true;
    }

    /**
     * Makes Player 1's worker build a level or dome to the new location provided 
     * if the current status is valid
     * @param worker The {@link Worker} that will build
     * @param new_x The x coordinate to move into
     * @param new_y The y coordinate to move into
     * @throws IllegalStateException if it is not Player 1's turn
     * @throws IllegalArgumentException if the provided location is an invalid build location
     */
    public void attemptBuildPlayer1Worker(Worker worker, int new_x, int new_y) {
        if (!isSetUp() || !isPlayer1Turn || !isBuildTurn || currWorker != worker) {
            throw new IllegalStateException("Bad Game status");
        }

        GodCard godcard = player1.getGodCard();
        
        if (godcard.isValidBuild(worker, new_x, new_y)) {
            godcard.build(worker, new_x, new_y);
        }
        else {
            throw new IllegalArgumentException("Invalid build");
        }

        if (continueBuild) {
            isPlayer1Turn = true;
            isBuildTurn = true;
        }
        else {
            isPlayer1Turn = false;
            isBuildTurn = false;
        }
    }

    /**
     * Makes Player 2's worker build a level or dome to the new location provided 
     * if the current status is valid
     * @param worker The {@link Worker} that will move
     * @param new_x The x coordinate to move into
     * @param new_y The y coordinate to move into
     * @throws IllegalStateException if it is Player 1's turn
     * @throws IllegalArgumentException if the provided location is an invalid build location
     */
    public void attemptBuildPlayer2Worker(Worker worker, int new_x, int new_y) {
        if (!isSetUp() || isPlayer1Turn || !isBuildTurn || currWorker != worker) {
            throw new IllegalStateException("Bad Game status");
        }

        GodCard godcard = player2.getGodCard();

        if (godcard.isValidBuild(worker, new_x, new_y)) {
            godcard.build(worker, new_x, new_y);
        }
        else {
            throw new IllegalArgumentException("Invalid build");
        }

        if (continueBuild) {
            isPlayer1Turn = false;
            isBuildTurn = true;
        }
        else {
            isPlayer1Turn = true;
            isBuildTurn = false;
        }
    }

    /**
     * Checks if the provided location is on the board
     * @param x The x coordinate of location
     * @param y The y coordinate of location
     * @return {@code true} if the location lies on the board
     */
    public boolean checkInBounds(int x, int y) {
        if (x < 0 || x >= 5) {
            return false;
        }

        if (y < 0 || y >= 5) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the given location is a neighbor of the current location
     * @param x The current x coordinate
     * @param y The current y coordinate
     * @param new_x The x coordinate to check if is a neighbor
     * @param new_y The y coordinate to check if is a neighbor
     * @return {@code true} if the two locations are neighbors of each other
     */
    public boolean checkIsNeighbor(int x, int y, int new_x, int new_y) {
        if ((Math.abs(new_x - x) > 1) || (Math.abs(new_y - y) > 1) || (new_x == x && new_y == y)) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the worker can move to the provided location
     * @param x The current x coordinate
     * @param y The current y coordinate
     * @param tile The current tile
     * @param new_x The x coordinate to move into
     * @param new_y The y coordinate to move into
     * @param new_tile The {@code Tile} that corresponds to the new tile
     * @return {@code true} if the worker can move into the new location
     */
    public boolean isValidMove(Worker worker, int new_x, int new_y) {
        GodCard godCard;
        if (isPlayer1Turn) {
            godCard = player1.getGodCard();
            
        }
        else {
            godCard = player2.getGodCard();
        }

        return godCard.isValidMove(worker, new_x, new_y);
    }

    /**
     * Checks if the worker can build at the provided location
     * @param x The current x coordinate
     * @param y The current y coordinate
     * @param new_x The x coordinate to move into
     * @param new_y The y coordinate to move into
     * @param new_tile The {@code Tile} that corresponds to the new tile
     * @return {@code true} if the worker can move into the new location
     */
    public boolean isValidBuild(Worker worker, int new_x, int new_y) {
        GodCard godCard;
        if (isPlayer1Turn) {
            godCard = player1.getGodCard();
            
        }
        else {
            godCard = player2.getGodCard();
        }

        return godCard.isValidBuild(worker, new_x, new_y);
    }
}
