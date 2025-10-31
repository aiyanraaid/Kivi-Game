package main;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;
import javax.swing.Timer;
public class GameEngine extends JPanel
{
    private static final long serialVersionUID = 1L;
    private String savePath;

    public KiviBoard game;
    public JFrame frame;
    public Player player1, player2, player3, player4;
    public Player activePlayer;
    private int rollsRemaining;
    private Random random;
    private int[] diceValues = {-1, -1, -1, -1, -1, -1};
    private Timer noMovesTimer;
    private ArrayList<GridSquare> possibleMoves;

    public GameEngine(JFrame frame, PlayerState state2, PlayerState state3, PlayerState state4){
        savePath = null;
        rollsRemaining = 3;
        this.frame = frame;
        random = new Random();
        possibleMoves = new ArrayList<>();
        // Initialize Players
        player1 = new HumanPlayer(KiviGame.color.playerColorA, 1);
        player2 = createPlayer(state2, KiviGame.color.playerColorB, 2);
        player3 = createPlayer(state3, KiviGame.color.playerColorC, 3);
        player4 = createPlayer(state4, KiviGame.color.playerColorD, 4);
        activePlayer = player1;
        
        game = new KiviBoard(frame, this);
        frame.setContentPane(game);
        frame.revalidate();
        frame.repaint();  
    }
    public void setPath(String path){savePath = path;}
    // Used in KiviBoard, important for updating individual dice values for partial rerolls.
    public void rollDice(int index){
        diceValues[index] = random.nextInt(6)+1;
    }

    
    public void nextTurn(){
        activePlayer.decrementStones();
        if (activePlayer == player1){game.updateStonePanel(player1, game.bottomPanel);}
        else if (activePlayer == player2){game.updateStonePanel(player2, game.leftPanel);}
        else if (activePlayer == player3){game.updateStonePanel(player3, game.subTopBottomPanel);}
        else if (activePlayer == player4){game.updateStonePanel(player4, game.rightPanel);}
        for (int i = 0; i < 6; i++){
            game.diceToggleButtons[i].setSelected(true);
            game.diceToggleButtons[i].setText("");
        }
        rollsRemaining = 3;
        nextActivePlayer();

        // Detects if game is over if next player has 0 stones.
        if (activePlayer.getStones() == 0){
            endGame();
        }
        else{
            game.setTurnLabel(activePlayer);
            // Computer turn starts here
            if (activePlayer instanceof ComputerPlayerHard){
                game.disableButtons();
                computerTurnHard();
            }
            else if (activePlayer instanceof ComputerPlayerEasy){
                game.disableButtons();
                computerTurnEasy();
            }
            else if (activePlayer instanceof HumanPlayer){
                game.enableButtons();
            }
        }   
    }

    private boolean  endGame(){
        game.setInfoLabel("Game Over...");
        Timer gameOverTimer = new Timer(2200, e -> {
            // Change to Game Over screen
            frame.setContentPane(new GameOverGUI(frame, this));
            frame.revalidate();
            frame.repaint();
        });
        gameOverTimer.setRepeats(false);
        gameOverTimer.start();

        //Deletes the current save since game is over. 
        File delFile = new File("data/saves/"+savePath);
        if (delFile.exists()){
            System.out.println("deleting file");
            savePath = null;
            return delFile.delete();}
        return false;
    }
    public void decrementRollsRemaining(){rollsRemaining -= 1;}
    public int getRollsRemaining(){return rollsRemaining;}
    public String getDice(int index){return String.valueOf(diceValues[index]);}
    public int getDiceInt(int index){return diceValues[index];}
    public void calculateMoves(){
        possibleMoves.clear();
        ArrayList<DiceCombination> validMoves = KiviGame.combinationMatcher.CombinationMatcher(diceValues);
        int count = 0;
        for (DiceCombination combo : validMoves){
            for (int x = 0; x < 7; x++){
                for (int y = 0; y < 7; y++){
                    // May have to change this to support special rules
                    if (combo == KiviGame.getCombo(x,y) && game.getTile(x,y).getOwner() == null){
                        //returnMoves.add(new Coordinate(x,y));
                        game.updateTile(x,y);
                        count++;
                        possibleMoves.add(game.gridSquares[x][y]);
                    }
                }
            }
        }
        if (count == 0 && rollsRemaining < 1){
            game.setInfoLabel("No possible moves! Next turn...");
            noMovesTimer = new Timer(1800, e -> {
                game.setInfoLabel("");
                nextTurn();
            });
            noMovesTimer.setRepeats(false);
            noMovesTimer.start();
        }
        else{
            game.setInfoLabel("No rerolls remaining. Please place a stone...");
        }
    }
    private Player createPlayer(PlayerState type, Color color, int id){
        Player player = null;
        if (type == PlayerState.HUMAN){player = new HumanPlayer(color, id);}
        else if (type == PlayerState.COMPUTER_EASY){player = new ComputerPlayerEasy(color, id);}
        else if (type == PlayerState.COMPUTER_HARD){player = new ComputerPlayerHard(color, id);}
        return player;
    }
    public void pauseGame(JFrame frame){
        frame.setContentPane(new PauseMenuGUI(frame, this));
        frame.revalidate();
        frame.repaint();
    }
    public void resumeGame(){
        game.scaleBoard();
        game.updateScale();
        updatePlayerColors();
        frame.setContentPane(game);
        game.resume();
        
    }
    public String getSavePath(){return savePath;}
    public Player getActivePlayer(){
        return activePlayer;
    }
    private void updatePlayerColors(){
        player1.setColor(KiviGame.color.playerColorA);
        if (player2 != null){player2.setColor(KiviGame.color.playerColorB);}
        if (player3 != null){player3.setColor(KiviGame.color.playerColorC);}
        if (player4 != null){player4.setColor(KiviGame.color.playerColorD);}
    }
    public void nextActivePlayer(){
        if (activePlayer == player1){
            if (player2 != null){activePlayer = player2;}
            else if (player3 != null){activePlayer = player3;}
            else if (player4 != null){activePlayer = player4;}
        }
        else if (activePlayer == player2){
            if (player3 != null){activePlayer = player3;}
            else if (player4 != null){activePlayer = player4;}
            else if (player1 != null){activePlayer = player1;}
        }
        else if (activePlayer == player3){
            if (player4 != null){activePlayer = player4;}
            else if (player1 != null){activePlayer = player1;}
            else if (player2 != null){activePlayer = player2;}
        }
        else if (activePlayer == player4){
            if (player1 != null){activePlayer = player1;}
            else if (player2 != null){activePlayer = player2;}
            else if (player3 != null){activePlayer = player3;}
        }
    }
    private int checkHighestValue(){
        int max = 0;
        for (int x = 0; x < 7; x++){
            for (int y = 0; y < 7; y++){
                if (game.gridSquares[x][y].getOwner() == null && game.gridSquares[x][y].getScore() > max){max = game.gridSquares[x][y].getScore();}
            }
        }
        return max;
    }
    /**
     * - Disable buttons while computer turn
     * - must be on timer (multiple timers?)
     * - maybe get rid of claimedtiles in player, maybe dont need coordinate, use gridsquare coords instead
     */
    private void computerTurnEasy(){
        int goal = checkHighestValue();
        ArrayList<GridSquare> goalTiles = new ArrayList<>();
        detectReroll();
        game.rollDice();
        Timer turnTimer = new Timer(1600, e -> {
            for (GridSquare tile : possibleMoves){
                if (tile.getScore() == goal){
                    goalTiles.add(tile);
                }
            }
            if(!goalTiles.isEmpty()){
                int r = random.nextInt(goalTiles.size());
                game.gridSquares[goalTiles.get(r).getXcoord()][goalTiles.get(r).getYcoord()].selectTile();
                return;
            }
            if (rollsRemaining > 0){
                computerTurnEasy();
            }
            else {
                for (GridSquare tile : possibleMoves){
                    if (tile.getScore() == goal-1){
                        game.gridSquares[tile.getXcoord()][tile.getYcoord()].selectTile();
                        return;
                    }
                    else if (tile.getScore() == goal-2){
                        game.gridSquares[tile.getXcoord()][tile.getYcoord()].selectTile();
                        return;
                    }  
                }
            }
        });
        turnTimer.setRepeats(false);
        turnTimer.start();
        

    }
    private void computerTurnHard(){
        // Checks what the highest value of available tiles is, changes to adapt play style if impossible to getter a higher score.
        int goal = checkHighestValue();
        // Temps for decision making
        ArrayList<GridSquare> goalTiles = new ArrayList<>();
        ArrayList<GridSquare> interruptOptions = new ArrayList<>();
        // Checks if no possible moves. Rerolls if possible or skips turn if no moves and no rerolls.
        // detectReroll is made obsolete and full roll is forced by KiviBoard if it is the first roll of a turn.
        detectReroll();
        game.rollDice();
        Timer turnTimer = new Timer(1600, e -> {
            // Turn taking logic
            for (GridSquare tile : possibleMoves){
                // Records any possible moves that will interrupt other players from making rows that are equal to the goal score.
                if (checkInterrupt(tile) && tile.getScore() == goal){interruptOptions.add(tile);}
                
                // Checks if any rows are possible to be made. If there are and the points from it are greater than the goal, immediately takes it.
                ArrayList<GridSquare> rowOptions = detectRow(tile.getXcoord(), tile.getYcoord());
                for (GridSquare option : rowOptions){
                    if ((tile.getScore() + option.getScore()) * 2 > goal){
                        game.gridSquares[tile.getXcoord()][tile.getYcoord()].selectTile();
                        return;
                    }
                }
            }
            // No rows able to be made. Checks if goal-point square is available that interrupts a player. 
            if (!interruptOptions.isEmpty()){
                int r = random.nextInt(interruptOptions.size());
                game.gridSquares[interruptOptions.get(r).getXcoord()][interruptOptions.get(r).getYcoord()].selectTile();
                return;
            }
            // If no goal-point interrupting squares but goal-point squares, takes random one.
            for (GridSquare tile : possibleMoves){
                if (tile.getScore() == goal){
                    goalTiles.add(tile);
                }
            }
            if(!goalTiles.isEmpty()){
                int r = random.nextInt(goalTiles.size());
                game.gridSquares[goalTiles.get(r).getXcoord()][goalTiles.get(r).getYcoord()].selectTile();
                return;
            }
            // No goal-point squares, no interruptions, no rows. If there are no remaining rolls, takes what it can get.
            if (rollsRemaining > 0){
                computerTurnHard();
            }
            // Don't need any further catches. No rerolls and no moves is handled implicitely.
            else {
                for (GridSquare tile : possibleMoves){
                    if (tile.getScore() == goal-1){
                        game.gridSquares[tile.getXcoord()][tile.getYcoord()].selectTile();
                        return;
                    }
                    else if (tile.getScore() == goal-2){
                        game.gridSquares[tile.getXcoord()][tile.getYcoord()].selectTile();
                        return;
                    }  
                }
            }
        });
        turnTimer.setRepeats(false);
        turnTimer.start();
        
    }
    private boolean checkInterrupt(GridSquare tile){
        if (tile.getXcoord()+1 <= 6){if (game.gridSquares[tile.getXcoord()+1][tile.getYcoord()].getOwner() != null && game.gridSquares[tile.getXcoord()+1][tile.getYcoord()].getOwner().getPlayerID() != activePlayer.getPlayerID()){return true;}}
        if (tile.getXcoord()-1 >= 0){if (game.gridSquares[tile.getXcoord()-1][tile.getYcoord()].getOwner() != null && game.gridSquares[tile.getXcoord()-1][tile.getYcoord()].getOwner().getPlayerID() != activePlayer.getPlayerID()){return true;}}
        if (tile.getYcoord()+1 <= 6){if (game.gridSquares[tile.getXcoord()][tile.getYcoord()+1].getOwner() != null && game.gridSquares[tile.getXcoord()][tile.getYcoord()+1].getOwner().getPlayerID() != activePlayer.getPlayerID()){return true;}}
        if (tile.getYcoord()-1 >= 0){if (game.gridSquares[tile.getXcoord()][tile.getYcoord()-1].getOwner() != null && game.gridSquares[tile.getXcoord()][tile.getYcoord()-1].getOwner().getPlayerID() != activePlayer.getPlayerID()){return true;}}
        return false;
    }
    // Basic placeholder
    private void detectReroll(){
        int tempCount = 0;
        int maxCount = 0;
        int diceIndex = -1;
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++){
                if (diceValues[i] == diceValues[j] && i != j){
                    tempCount += 1;
                }
            }
            if (tempCount > maxCount){
                maxCount = tempCount;
                diceIndex = i;
            }
        }
        if (maxCount > 3){
            for (int i = 0; i < 6; i++){
                if (diceValues[i] == diceValues[diceIndex]){
                    game.diceToggleButtons[i].setSelected(false);
                }
            }
        }
    }

    private ArrayList<GridSquare> detectRow(int x, int y){
        // Pass in coords of valid move, compares to surrounding
        // Update to check length of row
        ArrayList<GridSquare> rowTiles = new ArrayList<>();
        if (y+1 <= 6){if (game.gridSquares[x][y+1].getOwner() == activePlayer){rowTiles.add(game.getTile(x, y+1));}}
        if (y-1 >= 0){if (game.gridSquares[x][y-1].getOwner() == activePlayer){rowTiles.add(game.getTile(x, y-1));}}
        if (x+1 <= 6){if (game.gridSquares[x+1][y].getOwner() == activePlayer){rowTiles.add(game.getTile(x+1, y));}}
        if (x-1 >= 0){if (game.gridSquares[x-1][y].getOwner() == activePlayer){rowTiles.add(game.getTile(x-1, y));}}
        return rowTiles;
    }
}
