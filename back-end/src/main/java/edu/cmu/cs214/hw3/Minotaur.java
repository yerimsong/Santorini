package edu.cmu.cs214.hw3;

public class Minotaur implements GodCard {
    private Game game;
    
    public Minotaur(Game game) {
        this.game = game;
    }

    private Cell getCell(int x, int y) {
        Cell new_cell;
        try {
            new_cell = game.getBoard().getCell(x, y);
        } catch (Exception e) {
            throw new IllegalArgumentException("Index out of bounds on board");
        }
        return new_cell;
    }

    public boolean isValidMove(Worker worker, int new_x, int new_y) {
        // check if valid location
        int x = worker.getX();
        int y = worker.getY();
        Cell cell = worker.getCell();
        
        Cell new_cell;
        try {
            new_cell = game.getBoard().getCell(new_x, new_y);
        } catch (Exception e) {
            throw new IllegalArgumentException("Index out of bounds on board");
        }

        if (!game.checkIsNeighbor(x, y, new_x, new_y) || !game.checkInBounds(new_x, new_y)) {
            return false;
        }

        // check if valid level
        if (Math.abs(new_cell.getLevel() - cell.getLevel()) > 1) {
            return false;
        }

        // check if occupied by Dome
        if (new_cell.getIsOccupiedByDome()) {
            return false;
        }
        else if (new_cell.getIsOccupiedByPlayer()) {
            // check where opponent will be moving into
            int[] forcedSpace = calculateForcedSpace(x, y, new_x, new_y);
            int forced_x = forcedSpace[0];
            int forced_y = forcedSpace[1];
            if (!checkifForcedSpaceisValid(worker, forced_x, forced_y, new_x, new_y)) {
                return false;
            }
            Cell forced_tile = getCell(forced_x, forced_y);
            if (forced_tile.isOccupied()) {
                return false;
            }
        }

        return true;
    }

    public boolean isValidBuild(Worker worker, int new_x, int new_y) {
        int x = worker.getX();
        int y = worker.getY();
        
        Cell new_cell = getCell(new_x, new_y);

        // check if valid location
        if (!game.checkInBounds(new_x, new_y) || !game.checkIsNeighbor(x, y, new_x, new_y)) {
            return false;
        }

        // check if not occupied
        if (new_cell.isOccupied()) {
            return false;
        }

        return true;
    }

    private int[] calculateForcedSpace(int x, int y, int new_x, int new_y) {
        int res_x = new_x + (new_x - x);
        int res_y = new_y + (new_y - y);
        return new int[] {res_x, res_y};
    }

    private boolean checkifForcedSpaceisValid(Worker worker, int x, int y, int old_x, int old_y) {
        if (!game.checkInBounds(x, y)) {
            return false;
        }
        if (game.getBoard().getCell(x, y).isOccupied()) {
            return false;
        }
        if ((game.getBoard().getCell(old_x, old_y).getWorker().getPlayer()).equals(worker.getPlayer())) {
            return false;
        }
        return true;
    }

    public void move(Worker worker, int new_x, int new_y) {
        int x = worker.getX();
        int y = worker.getY();
        worker.setX(new_x);
        worker.setY(new_y);

        Cell cell = getCell(x, y);
        Cell new_cell = getCell(new_x, new_y);

        // empty spot where worker moved FROM
        cell.setIsOccupiedByPlayer(false);
        cell.setWorker(null);
        // get the worker that will be pushed out
        Worker pushedWorker = new_cell.getWorker();

        // move Worker into new spot
        worker.setCell(new_cell);
        new_cell.setIsOccupiedByPlayer(true);
        new_cell.setWorker(worker);

        // How the worker will be forced into the new space
        // modify the spot the worker will be pushed into if necessary
        if (pushedWorker != null) {
            // find the space the worker will be pushed into
            int[] forcedSpace = calculateForcedSpace(x, y, new_x, new_y);
            Cell forcedCell = getCell(forcedSpace[0], forcedSpace[1]);  
            // modify the tile 
            forcedCell.setWorker(pushedWorker);
            forcedCell.setIsOccupiedByPlayer(true);
            // modify the worker
            pushedWorker.setX(forcedSpace[0]);
            pushedWorker.setY(forcedSpace[1]);
            pushedWorker.setCell(forcedCell);
        }
        
    }

    public void build(Worker worker, int new_x, int new_y) {
        Cell new_cell = getCell(new_x, new_y);
        new_cell.increaseLevel();
        if (new_cell.getLevel() == 4) {
            new_cell.setIsOccupiedByDome(true);
        }
    }
}
