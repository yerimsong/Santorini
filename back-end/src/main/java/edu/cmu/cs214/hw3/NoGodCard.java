package edu.cmu.cs214.hw3;

public class NoGodCard implements GodCard {
    private Game game;
    
    public NoGodCard(Game game) {
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
        Cell new_cell = getCell(new_x, new_y);

        if (!game.checkIsNeighbor(x, y, new_x, new_y) || !game.checkInBounds(new_x, new_y)) {
            System.out.println("invalid neighbor");
            return false;
        }

        // check if valid level
        if (Math.abs(new_cell.getLevel() - cell.getLevel()) > 1) {
            System.out.println("Invalid level: move from " + cell.getLevel() + " to " + new_cell.getLevel());
            return false;
        }

        // check if not occupied by workers
        if (new_cell.isOccupied()) {
            System.out.println("occupied cell");
            return false;
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

        // check if not occupied by workers
        if (new_cell.isOccupied()) {
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

        worker.setCell(new_cell);
        cell.setIsOccupiedByPlayer(false);
        cell.setWorker(null);
        new_cell.setIsOccupiedByPlayer(true);
        new_cell.setWorker(worker);
    }

    public void build(Worker worker, int new_x, int new_y) {
        Cell new_cell = getCell(new_x, new_y);
        new_cell.increaseLevel();
        if (new_cell.getLevel() == 4) {
            new_cell.setIsOccupiedByDome(true);
        }
    }

}