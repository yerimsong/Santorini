// package game;
package edu.cmu.cs214.hw3;

import java.io.IOException;
import java.util.Map;
import fi.iki.elonen.NanoHTTPD;

public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private Game game;

    /**
     * Start the server at :8080 port.
     * @throws IOException
     */
    public App() throws IOException {
        super(8080);

        this.game = new Game();

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    /* (non-Javadoc)
     * @see fi.iki.elonen.NanoHTTPD#serve(fi.iki.elonen.NanoHTTPD.IHTTPSession)
     */
    @Override
    public Response serve(IHTTPSession session) {
        boolean haserror = false;
        boolean continueBuild = false;
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        
        if (uri.equals("/newgame")) {
            this.game = new Game();
        } 
        else if (uri.equals("/initialize")) {
            GodCard gc1 = new NoGodCard(game);
            GodCard gc2 = new NoGodCard(game);
            if (params.get("gc1").equals("demeter")) {
                gc1 = new Demeter(game);
            }
            else if (params.get("gc1").equals("minotaur")) {
                gc1 = new Minotaur(game);
            }
            else if (params.get("gc1").equals("pan")) {
                gc1 = new Pan(game);
            }
            if (params.get("gc2").equals("demeter")) {
                gc2 = new Demeter(game);
            }
            else if (params.get("gc2").equals("minotaur")) {
                gc2 = new Minotaur(game);
            }
            else if (params.get("gc2").equals("pan")) {
                gc2 = new Pan(game);
            }
            try {
                this.game.initializePlayer1Workers(
                    Integer.parseInt(params.get("x1")), 
                    Integer.parseInt(params.get("y1")), 
                    Integer.parseInt(params.get("x2")), 
                    Integer.parseInt(params.get("y2")), 
                    gc1);
                this.game.initializePlayer2Workers(
                    Integer.parseInt(params.get("x3")), 
                    Integer.parseInt(params.get("y3")), 
                    Integer.parseInt(params.get("x4")), 
                    Integer.parseInt(params.get("y4")), 
                    gc2);
            } catch (Exception e) {
                haserror = true;
            }
            
        }
        else if (uri.equals("/selectworker")) {
            Cell cell = this.game.getBoard().getCell(Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
            try {
                this.game.setWorker(cell.getWorker());
            } catch (Exception e) {
                haserror = true;
            }
        }
        else if (uri.equals("/move")) {
            Worker worker = this.game.getCurrentWorker();
            try {
                if (worker.getPlayer().equals("Player 1")) {
                    this.game.attemptMovePlayer1Worker(worker, Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
                }
                else if (worker.getPlayer().equals("Player 2")) {
                    this.game.attemptMovePlayer2Worker(worker, Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
                }
            } catch (Exception e) {
                haserror = true;
            }
        }
        else if (uri.equals("/build")) {
            Worker worker = this.game.getCurrentWorker();
            try {
                if (worker.getPlayer().equals("Player 1")) {
                    this.game.attemptBuildPlayer1Worker(worker, Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
                }
                else if (worker.getPlayer().equals("Player 2")) {
                    this.game.attemptBuildPlayer2Worker(worker, Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
                }
                if (this.game.getContinueBuild()) {
                    continueBuild = true;
                }
            } catch (Exception e) {
                haserror = true;
            }
        }
        // Extract the view-specific data from the game and apply it to the template.
        GameState gameplay = GameState.forGame(this.game);
        return newFixedLengthResponse(gameplay.generateJSONString(haserror, continueBuild));
    }
}