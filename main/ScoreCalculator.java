package main;

import java.util.HashMap;
import java.util.Map;

public class ScoreCalculator 
{
    private Map<Player, Integer> scores;
    private boolean[][] counted;
    private GameEngine gameEngine;
    private KiviBoard game;
    private GridSquare[][] board;

    public ScoreCalculator(GameEngine gameEngine){
        this.gameEngine = gameEngine;
        game = gameEngine.game;
        board = game.gridSquares;
        scores = new HashMap<>();
        counted = new boolean[7][7];
    }

    public Map<Player, Integer> calculateScores(){
        scores.clear();
        counted = new boolean[7][7];

        for (int x = 0; x < 7; x++){
            for (int y = 0; y < 7; y++){
                Player owner = board[x][y].getOwner();
                if (owner!= null){
                    int horizontalScore = getHorizontalScore(x, y, owner);
                    int verticalScore = getVerticalScore(x, y, owner);
                    int totalScore = horizontalScore + verticalScore;
                    scores.put(owner, scores.getOrDefault(owner, 0) + totalScore); // Adds or updates a players score
                }
            }
        }

        addLoneTiles(); // Adds any owned tiles that weren't included in any rows
        return scores;
    }

    private int getHorizontalScore(int startX, int startY, Player owner){
        if (counted[startX][startY]){return 0;}
        int total = 0;
        int subTotal = 0;
        int length = 0;
        int x = startX;

        while (x < 7 && board[x][startY].getOwner() == owner){
            subTotal += board[x][startY].getScore();
            length++;
            x++;
        }
        if (length >1){
            for (int i = startX; i < startX + length; i++){
                counted[i][startY] = true;
            }
            total = subTotal * length;
        }
        return total;
    }

    private int getVerticalScore(int startX, int startY, Player owner){
        int total = 0;
        int subTotal = 0;
        int length = 0;
        int y = startY;
        while (y < 7 && board[startX][y].getOwner() == owner){
            subTotal += board[startX][y].getScore();
            length++;
            y++;
        }
        if (length > 1){
            for (int i = startY; i < startY + length; i++){
                counted[startX][i] = true;
            }
            total = subTotal * length;
        }
        return total;
    }


    private void addLoneTiles(){
        for (int x = 0; x < 7; x++){
            for (int y = 0; y < 7; y++){
                Player owner = board[x][y].getOwner();
                if (owner != null && !counted[x][y]){
                    scores.put(owner, scores.getOrDefault(owner, 0) + board[x][y].getScore());
                }
            }
        }
    }

/**
    private int[] scores;

    
    public int[] getScore(GameEngine gameEngine){
        scores = new int[] {-1,-1,-1,-1};
        if (gameEngine.player1 != null){scores[0] = calculateScore(gameEngine.player1, gameEngine.game);}
        if (gameEngine.player2 != null){scores[1] = calculateScore(gameEngine.player2, gameEngine.game);}
        if (gameEngine.player3 != null){scores[2] = calculateScore(gameEngine.player3, gameEngine.game);}
        if (gameEngine.player4 != null){scores[3] = calculateScore(gameEngine.player4, gameEngine.game);}
        return scores;
    }
    private int calculateScore(Player player, KiviBoard game){
        int score = 0;
        score += calculateHorizontal(player, game);
        score += calculateVertical(player, game);
        for (int x = 0; x < 7; x++){
            for (int y = 0; y < 7; y++){
                if (game.gridSquares[x][y].getOwner().getPlayerID() == player.getPlayerID() && !game.gridSquares[x][y].isChecked()){
                    score+= game.gridSquares[x][y].getScore();
                }
            }
        }

        return score;
    }
    private int calculateHorizontal(Player player, KiviBoard game){
        int score = 0;
        for (int x = 0; x < 7; x++){
            ArrayList<Integer> yVals = new ArrayList<>();
            for (int y = 0; y < 7; y++){
                if (game.gridSquares[x][y].getOwner().getPlayerID() == player.getPlayerID()){
                    game.gridSquares[x][y].check();
                    yVals.add(y);
                }
            }
            int tempScore = 0;
            int multiplier = 1;
            ArrayList<Integer> tempRow = new ArrayList<>();
            for (int i = 0; i < yVals.size()-1; i++){
                if (yVals.get(i+1) == yVals.get(i)+1){tempRow.add(yVals.get())}
            }
        }

        return score;
    }
    private int calculateVertical(Player player, KiviBoard game){
        int score = 0;

        return score;
    } */
}