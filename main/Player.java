package main;
import java.awt.*;
import java.io.Serializable;
public abstract class Player implements Serializable
{
    private int points; //Not calculated until end of game
    private int stones;
    private Color color;
    private int playerID;
    //public Player(params) for loading
    public Player(Color color, int playerID){
        stones = 10;
        points = 0;
        this.color = color;
        this.playerID = playerID;
    }
    public int getPlayerID(){return playerID;}
    public void decrementStones(){stones -= 1;}
    public int getPoints(){return points;}
    public int getStones(){return stones;}
    public Color getColor(){return color;}
    public void setColor(Color color){this.color = color;}
}