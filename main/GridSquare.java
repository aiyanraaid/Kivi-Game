package main;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/*
 *  A GUI component
 *
 *  A simple extension of JPanel which records its
 *  coordinates in xcoord and ycoord, NOT in 'x' and 'y'.
 *  Why not? Because 'x' and 'y' are already attributes of
 *  the panel (super) class which say where to draw it in the window.
 *
 *  The game grid and allows the background colour to be set with ease.
 *
 *  @author mhatcher and Cole Huntley
 */
public class GridSquare extends JLayeredPane {
    private int xcoord, ycoord; // location in the grid
    private LineBorder blackB, whiteB;
    private Color squareColor;
    private int worth;
    private String tokenStatus;
    private DiceCombination combo;
    private Player owner;
    public JButton tileButton;
    private boolean valid;
    public GameEngine gameEngine;
    public KiviBoard game;
    private Stone stone;
    private boolean checked;

    // constructor takes the x and y coordinates of this square
    public GridSquare(int xcoord, int ycoord, Icon image, KiviBoard game, GameEngine gameEngine) {
        this.game = game;
        this.gameEngine = gameEngine;
        this.setSize(50, 50);
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        checked = false;

        setLayout(null);
        setOpaque(false);
        setPreferredSize(new Dimension(image.getIconWidth(), image.getIconHeight()));

        tileButton = new JButton(image);
        tileButton.setOpaque(false);
        tileButton.setContentAreaFilled(false);
        tileButton.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
        tileButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        tileButton.setDisabledIcon(image); // Used to keep board looking normal when all buttons are disabled for computer turns.
        add(tileButton, JLayeredPane.DEFAULT_LAYER);

        tileButton.addActionListener(e -> selectTile());

        whiteB = new LineBorder(Color.white, 5);
        blackB = new LineBorder(Color.black, 5);
        combo = KiviGame.getCombo(xcoord, ycoord);
        worth = combo.getScore();
        owner = null;
    }

    public void restoreTransientFields(){
        tileButton.addActionListener(e -> selectTile());
    }
    public boolean isValid(){return valid;}
    protected void selectTile() {
        if (valid) {
            owner = gameEngine.getActivePlayer();
            stone = new Stone(owner.getColor(), 35);
            stone.setBounds(7, 7, 35, 35);
            add(stone, JLayeredPane.PALETTE_LAYER);
            revalidate();
            repaint();
            setValid(false);
            game.resetMoves();
            gameEngine.nextTurn();
        }
    }
    public void updateSize(Icon image){
        setPreferredSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
        tileButton.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
    }

    public void check(){checked = true;}
    public boolean isChecked(){return checked;}
    public void uncheck(){checked = false;}

    protected void updateTileColor() {
        stone.setColor(owner.getColor());
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Player getOwner() {
        return owner;
    }

    public void assignOwner(Player owner) {
        this.owner = owner;
    }

    public void setColor(int decider) {
        decider = decider % 2;
        if (decider == 0) {
            this.setBackground(Color.WHITE);
            this.setBorder(blackB);
        } else {
            this.setBackground(Color.BLACK);
            this.setBorder(whiteB);
        }
    }

    public void setImage(Icon image) {
        tileButton.setIcon(image);
    }

    // if the square is black it becomes white, and vice-versa
    public void switchColor() {
        Color colour = (getBackground() == Color.black) ? Color.white : Color.black;
        this.setBackground(colour);
    }

    // simple setters and getters

    public void setXcoord(int value) {
        xcoord = value;
    }

    public void setYcoord(int value) {
        ycoord = value;
    }

    public int getXcoord() {
        return xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }

    public void setTokenStatus(String value) {
        tokenStatus = value;
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public void setSquareColor(Color value) {
        squareColor = value;
    }

    public Color getSquareColor() {
        return squareColor;
    }

    public DiceCombination getCombo() {
        return combo;
    }

    public int getScore() {
        return worth;
    }

    public void setTileBorder(Border border) {
        tileButton.setBorder(border);
        revalidate();
        repaint();
    }
}
