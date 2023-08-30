## Starting a Game

### Set Up Backend Server

Either run the Java backend by using your IDE or by typing

```
mvn exec:exec
```

in the back-end folder. This will start the Java server at http://localhost:8080.

### Set Up Frontend Server

In the front-end folder, run

```
npm install
npm start
```

This will start the front-end server at http://localhost:3000. You can update the front-end code as the server is running in the development mode (i.e., npm start). It will automatically recompile and reload.

### Playing the Game

Once a new game has started, begin by initializing the players. This can be done by inputting values for worker locations and selecting player godcards at the top of the screen. Click submit once all fields have valid values. Then, play the game by the rules of Santorini.

Note: The Demeter god card can skip the second build by clicking the selected worker's current location

## Santorini Rules

Santorini has very simple rules, but the game is very extensible. You can find the original rules [online](https://roxley.com/products/santorini). Beyond the actual board game, you can also find an App that implements the game if you want to try to play it.

In a nutshell, the rules are as follows: The game is played on a 5 by 5 grid, where each grid can contain towers consisting of blocks and domes. Two players have two workers each on any field of the grid. Throughout the game, the workers move around and build towers. The first worker to make it on top of a level-3 tower wins. Note that though the official rules require that if a player cannot further move any worker, she/he will lose, you don't need to consider this as a winning condition in this homework. You also don’t need to handle more than two players.

As set up, both players pick starting positions for both their workers on the grid. (For simplicity, in Homework 3 and 5, **you can assume a player (e.g. Player A) always starts first**). Players take turns. In each turn, they select one of their workers, move this worker to an adjacent unoccupied field, and afterward add a block or dome to an unoccupied adjacent field of their new position. Locations with a worker or a dome are considered occupied. Workers can only climb a maximum of one level when moving. Domes can only be built on level-3 towers. You can assume there are infinite pieces to play.

That's it. You probably want to play a few rounds to get a feel for the game mechanics. There are god powers that modify the game behavior, but those will not be relevant until Homework 5.
