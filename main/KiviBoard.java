package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

import java.io.Serializable;
public class KiviBoard extends JPanel implements ActionListener, Serializable
{
    protected JPanel topPanel, bottomPanel, leftPanel, rightPanel, middlePanel;    // top and bottom panels in the main window
    private JLabel instructionLabel;        // a text label to tell the user what to do
    private JLabel infoLabel;   // a text label to show the coordinate of the selected square
    private JLabel points1,points2,points3,points4; //shows player points
    protected JPanel subTopUpPanel;
    private JLabel turnIndicator;
    protected JPanel subTopBottomPanel;
    private JPanel dicePanel;   //dice panel next to P1: stones 
    protected GridSquare [][] gridSquares;    // squares to appear in grid formation in the bottom panel
    private int rows,columns, length, buttonLen;   // the size of the grid
    private JButton pauseButton;
    private GameEngine gameEngine;
    public JFrame frame;
    protected JToggleButton[] diceToggleButtons; 
    private ImageIcon dice1, dice2, dice3, dice4, dice5, dice6;
    private ImageIcon[] diceIcons;
    private ImageIcon diceSelected1, diceSelected2, diceSelected3, diceSelected4, diceSelected5, diceSelected6;
    private ImageIcon[] diceSelectedIcons;
    private int diceSize;
    private int fontSize;
    private int stoneSize;
    private JPanel stoneWrapper1,stoneWrapper2, stoneWrapper3, stoneWrapper4;
    private String[] unselectedPaths;
    private String[] selectedPaths;
    protected JToggleButton diceButton1, diceButton2, diceButton3, diceButton4, diceButton5, diceButton6;
    private JButton diceButton;

    JPanel centerWrapper;
    private static final long serialVersionUID = 1L;
    /*
     *  constructor method takes as input how many rows and columns of gridsquares to create
     *  it then creates the panels, their subcomponents and puts them all together in the main frame
     *  it makes sure that action listeners are added to selectable items
     *  it makes sure that the gui will be visible
     */
    public KiviBoard(JFrame frame, GameEngine gameEngine)
    {
        this.gameEngine = gameEngine;
        this.frame = frame;
        rows = 7;
        columns = 7;
        scaleBoard();
        
        //length = 350;
        //buttonLen = 50;
        //diceSize = 30; //change later to be a function of resolution
        //if(KiviGame.settings.screenWidth == 1000){
            //length = 448;
           // buttonLen = 64;
            //diceSize = 40;
        //}else if(KiviGame.settings.screenWidth == 1920){
            //length = 560;
            //buttonLen = 80;
            //diceSize = 60;
        //}
        //create the panels
        topPanel = new JPanel();
        subTopUpPanel = new JPanel();
        subTopBottomPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        bottomPanel = new JPanel();
        //manage middlePanel size
        middlePanel = new JPanel(new GridLayout(rows, columns));
        
        //create wrappers to manage space
        stoneWrapper1 = new JPanel(new GridBagLayout());
        stoneWrapper2 = new JPanel(new GridBagLayout());
        stoneWrapper3 = new JPanel(new GridBagLayout());
        stoneWrapper4 = new JPanel(new GridBagLayout());
        centerWrapper = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;  // Allow resizing
        centerWrapper.add(middlePanel, gbc);
        
        //Panel layout managers
        topPanel.setLayout(new GridLayout(2,1,0,10));
        subTopUpPanel.setLayout(new GridLayout(1,4,10,10));
        subTopBottomPanel.setLayout(new GridLayout(1,11));
        leftPanel.setLayout(new GridLayout(11,1));
        rightPanel.setLayout(new GridLayout(11,1));
        bottomPanel.setLayout(new GridLayout(1,11));
        
        
        pauseButton = new JButton("Pause");
        pauseButton.setPreferredSize(new Dimension(3*diceSize,diceSize));
        pauseButton.setBackground(KiviGame.color.buttonColorB);

        //topPanel.add(instructionLabel);
        JPanel pauseButtonPanel = new JPanel();
        pauseButtonPanel.add(pauseButton);
        subTopUpPanel.add(pauseButtonPanel);
        
        
        JPanel textPanel = new JPanel(new GridLayout(2,1,0,10));
        JPanel infoLabelPanel = new JPanel();
        //infoLabel = new JLabel("");
        //infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        //infoLabel.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        //infoLabelPanel.add(infoLabel);
        turnIndicator = new JLabel("Player 1's turn...");
        turnIndicator.setFont(new Font("Arial", Font.PLAIN, fontSize));
        JPanel turnIndicatorPanel = new JPanel();
        turnIndicatorPanel.add(turnIndicator);
        textPanel.add(turnIndicatorPanel);
        textPanel.add(infoLabelPanel);
        subTopUpPanel.add(textPanel);
        
        JPanel diceButtonPanel = new JPanel();
        diceButton = new JButton("Roll Dice");
        diceButton.setPreferredSize(new Dimension(3*diceSize,diceSize));
        diceButton.setBackground(KiviGame.color.buttonColorB);
        diceButtonPanel.add(diceButton);
        subTopUpPanel.add(diceButtonPanel);
        diceButton.addActionListener(e -> rollDice());
        
        // Initializing dice buttons
        diceButton1 = new JToggleButton("", true);
        diceButton1.setPreferredSize(new Dimension(diceSize, diceSize));
        diceButton2 = new JToggleButton("", true);
        diceButton2.setPreferredSize(new Dimension(diceSize, diceSize));
        diceButton3 = new JToggleButton("", true);
        diceButton3.setPreferredSize(new Dimension(diceSize, diceSize));
        diceButton4 = new JToggleButton("", true);
        diceButton4.setPreferredSize(new Dimension(diceSize, diceSize));
        diceButton5 = new JToggleButton("", true);
        diceButton5.setPreferredSize(new Dimension(diceSize, diceSize));
        diceButton6 = new JToggleButton("", true);
        diceButton6.setPreferredSize(new Dimension(diceSize, diceSize));
        diceToggleButtons = new JToggleButton[] {diceButton1, diceButton2, diceButton3, diceButton4, diceButton5, diceButton6};
        
        selectedPaths = new String[] {"resources/dice_one.png","resources/dice_two.png","resources/dice_three.png","resources/dice_four.png","resources/dice_five.png","resources/dice_six.png"};
        unselectedPaths = new String[] {"resources/dice_one_selected.png","resources/dice_two_selected.png","resources/dice_three_selected.png","resources/dice_four_selected.png","resources/dice_five_selected.png","resources/dice_six_selected.png"};
        
        for (int i = 0; i < 6; i++){
            diceToggleButtons[i].addItemListener(e -> {
                if (gameEngine.getRollsRemaining() == 3) {   // Before first roll
                    ((JToggleButton) e.getSource()).setSelected(true);  // Keep dice ON
                }
            });
            
            diceToggleButtons[i].setIconTextGap(0);
            diceToggleButtons[i].setContentAreaFilled(false);
            diceToggleButtons[i].setIcon(scaledDiceIcon(unselectedPaths[i], diceSize));
            diceToggleButtons[i].setSelectedIcon(scaledDiceIcon(selectedPaths[i], diceSize));
        }
        
        JPanel dicePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcDice = new GridBagConstraints();
        gbcDice.fill = GridBagConstraints.NONE;
        gbcDice.insets = new Insets(2,2,2,2);
        gbcDice.weightx = 0;
        gbcDice.weighty = 1;
        gbcDice.gridx = 0; gbcDice.gridy = 0; dicePanel.add(diceToggleButtons[0], gbcDice);
        gbcDice.gridx = 0; gbcDice.gridy = 1; dicePanel.add(diceToggleButtons[1], gbcDice);
        gbcDice.gridx = 1; gbcDice.gridy = 0; dicePanel.add(diceToggleButtons[2], gbcDice);
        gbcDice.gridx = 1; gbcDice.gridy = 1; dicePanel.add(diceToggleButtons[3], gbcDice);
        gbcDice.gridx = 2; gbcDice.gridy = 0; dicePanel.add(diceToggleButtons[4], gbcDice);
        gbcDice.gridx = 2; gbcDice.gridy = 1; dicePanel.add(diceToggleButtons[5], gbcDice);
        //for (int i = 0; i < 6; i++){dicePanel.add(diceToggleButtons[i]);}
        subTopUpPanel.add(dicePanel);
        
        //all three have to be here to work for some reason
        centerWrapper.setPreferredSize(new Dimension(length,length));
        centerWrapper.setMaximumSize(new Dimension(length,length));
        centerWrapper.setMinimumSize(new Dimension(length,length));
        middlePanel.setPreferredSize(new Dimension(length,length));
        middlePanel.setMaximumSize(new Dimension(length,length));
        middlePanel.setMinimumSize(new Dimension(length,length));
        //stoneWrapper1.setMaximumSize(new Dimension(diceSize,diceSize));
 
        // create the squares and stones and add to board
        gridSquares = new GridSquare[rows][columns];
        for ( int x = 0; x < columns; x ++)
        {
            for ( int y = 0; y < rows; y ++)
            {
                ImageIcon icon = new ImageIcon("resources/piece"+x+y+".png");
                Image img = icon.getImage().getScaledInstance(buttonLen, buttonLen, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(img);
            
                gridSquares[x][y] = new GridSquare(x, y,scaledIcon, this, gameEngine);

                gridSquares[x][y].tileButton.addActionListener(this);     //this   // AGAIN, don't forget this line!
                middlePanel.add(gridSquares[x][y]);
            }
        }
        
        //add panels to wrappers
        topPanel.add(subTopUpPanel);
        topPanel.add(stoneWrapper2);
        stoneWrapper1.add(leftPanel);
        stoneWrapper2.add(subTopBottomPanel);
        stoneWrapper3.add(rightPanel);
        stoneWrapper4.add(bottomPanel);
        pauseButton.addActionListener(e -> gameEngine.pauseGame(frame));
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(stoneWrapper1,BorderLayout.WEST);
        add(stoneWrapper3,BorderLayout.EAST);
        add(stoneWrapper4, BorderLayout.SOUTH); // needs to be center or will draw too small
        add(centerWrapper, BorderLayout.CENTER);
        
        // Used to update stones display after a turn.
        updateStonePanel(gameEngine.player1, bottomPanel);
        updateStonePanel(gameEngine.player2, leftPanel);
        updateStonePanel(gameEngine.player3, subTopBottomPanel);
        updateStonePanel(gameEngine.player4, rightPanel);
    }

    public void restoreTransientFields(){
        pauseButton.addActionListener(e -> {
            System.out.println("pause button pressed");
            gameEngine.pauseGame(frame);
            System.out.println("gameEngine.pauseGame called");
        });
        System.out.println("pause button actioner listener added back");
        diceButton.addActionListener(e -> rollDice());
        updateStonePanel(gameEngine.player1, bottomPanel);
        updateStonePanel(gameEngine.player2, leftPanel);
        updateStonePanel(gameEngine.player3, subTopBottomPanel);
        updateStonePanel(gameEngine.player4, rightPanel);
        //Grid Squares
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++){
                gridSquares[i][j].restoreTransientFields();
            }
        }
    }

    public void rollDice(){
        //rolls remaining decrements each roll. Only reset after a stone is placed.
        // Should include a JLabel update for when max dice rolls is reached.
        if (gameEngine.getRollsRemaining() > 0){
            resetMoves();
            for (int i = 0; i < 6; i++){
                if (diceToggleButtons[i].isSelected()){
                    gameEngine.rollDice(i);
                    //diceToggleButtons[i].setText(gameEngine.getDice(i));
                    diceToggleButtons[i].setSelectedIcon(scaledDiceIcon(selectedPaths[gameEngine.getDiceInt(i)-1], diceSize));
                    diceToggleButtons[i].setIcon(scaledDiceIcon(unselectedPaths[gameEngine.getDiceInt(i)-1], diceSize));
                }
            }
            gameEngine.decrementRollsRemaining();
            gameEngine.calculateMoves();
        }
        middlePanel.revalidate();
        middlePanel.repaint();
    }
    public void setInfoLabel(String string){
        //infoLabel.setText(string);
        // info label blank right now because it will bug game since info 
        // label is not added in gui
        revalidate();
        repaint();
    }
    public void resetMoves(){
        for (int x = 0; x < 7; x++){
            for (int y = 0; y < 7; y++){
                gridSquares[x][y].setTileBorder(new LineBorder(Color.GRAY,1));
                gridSquares[x][y].setValid(false);
            }
        }
        revalidate();
        repaint();
    }
    public void updateTile(int x, int y){
        gridSquares[x][y].setTileBorder(BorderFactory.createLineBorder(new Color(0,252,207), 4));
        gridSquares[x][y].setValid(true);
        revalidate();
        repaint();
    }
    public void setTurnLabel(Player player){
        turnIndicator.setText("Player "+String.valueOf(player.getPlayerID())+"'s turn...");
        turnIndicator.setFont(new Font("Arial", Font.PLAIN, 20));
    }
    public void updateStonePanel(Player player, JPanel panel){
        panel.removeAll();
        if (player != null){
            
            panel.add(new JLabel("Player "+String.valueOf(player.getPlayerID())));
            
            int stones = player.getStones();
            JButton[] tempStones = new JButton[stones];
            for(int i = 0; i < stones; i++){
                tempStones[i] = new JButton(String.valueOf(i+1));
                tempStones[i].setBackground(player.getColor());
                panel.add(tempStones[i]);
            }
        }
        frame.revalidate();
        frame.repaint();
    }
    
    public void resume(){
        scaleBoard();
        updateColors();
        revalidate();
        repaint();
    }
    private void updateColors(){
        // Redrawing all player stone panels
        updateStonePanel(gameEngine.player1, bottomPanel);
        updateStonePanel(gameEngine.player2, leftPanel);
        updateStonePanel(gameEngine.player3, subTopBottomPanel);
        updateStonePanel(gameEngine.player4, rightPanel);
        
        // Updating token colors on board
        for ( int x = 0; x < columns; x ++)
        {for ( int y = 0; y < rows; y ++){
                if (gridSquares[x][y].getOwner() != null){
                    gridSquares[x][y].updateTileColor();
        }}}
    }
    public void actionPerformed(ActionEvent aevt){
        Object selected = aevt.getSource();
    }

    public GridSquare getTile(int x, int y){
        return gridSquares[x][y];
    }
    private ImageIcon scaledDiceIcon(String file, int size){
        // Used for scaling size of dice panel with resolution
        ImageIcon icon = new ImageIcon(file);
        Image dice = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(dice);
    }
    public ArrayList<ArrayList<int[]>> getHorizontalRows() {
        ArrayList<ArrayList<int[]>> horizontalRows = new ArrayList<>();

        for (int x = 0; x < columns; x++) {
            ArrayList<int[]> currentRow = new ArrayList<>();
        
            for (int y = 0; y < rows; y++) {
                GridSquare square = gridSquares[x][y];
            
                if (square.getOwner() != null) {
                    int xCoord = square.getXcoord();
                    int yCoord = square.getYcoord();
                    int[] coords = {xCoord,yCoord};
                    currentRow.add(coords);
                }  
                if(x == 6){
                    horizontalRows.add(new ArrayList<int[]>(currentRow));
                    currentRow.clear();
                    }
                }    
                }
        return horizontalRows;
    }


    public ArrayList<ArrayList<int[]>> getVerticalRows() {
        ArrayList<ArrayList<int[]>> verticalRows = new ArrayList<>();

        for (int y = 0; y < rows; y++) {
            ArrayList<int[]> currentColumn = new ArrayList<>();
        
            for (int x = 0; x < columns; x++) {
                GridSquare square = gridSquares[x][y];
            
                if (square.getOwner() !=  null) {
                    int[] coords = {x,y};
                    currentColumn.add(coords);
                    
                } 
                if (y == 6){
                    verticalRows.add(new ArrayList<int[]>(currentColumn));
                    currentColumn.clear();
                }
            }
        }
    
        return verticalRows;
    }
    public void scaleBoard(){
        if(KiviGame.settings.screenWidth == 1000){
            length = 448;
            buttonLen = 64;
            diceSize = 50;
            fontSize = 30;
            stoneSize = 45;
        }else if(KiviGame.settings.screenWidth == 1920){
            length = 490;
            buttonLen = 70;
            diceSize = 70;
            fontSize = 32;
            stoneSize = 60;
        }else{
            length = 350;
            buttonLen = 50;
            diceSize = 30;
            fontSize = 22;
            stoneSize = 35;
        }
        
    }
    public void updateScale(){
        scaleBoard();
        centerWrapper.setPreferredSize(new Dimension(length,length));
        centerWrapper.setMaximumSize(new Dimension(length,length));
        centerWrapper.setMinimumSize(new Dimension(length,length));
        middlePanel.setPreferredSize(new Dimension(length,length));
        middlePanel.setMaximumSize(new Dimension(length,length));
        middlePanel.setMinimumSize(new Dimension(length,length));
        for ( int x = 0; x < columns; x ++)
        {
            for ( int y = 0; y < rows; y ++)
            {
                ImageIcon icon = new ImageIcon("resources/piece"+x+y+".png");
                Image img = icon.getImage().getScaledInstance(buttonLen, buttonLen, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(img);
                gridSquares[x][y].setImage(scaledIcon);
                gridSquares[x][y].updateSize(scaledIcon);
                middlePanel.add(gridSquares[x][y]);
            }
        }
        for (int i = 0; i < 6; i++){
            diceToggleButtons[i].addItemListener(e -> {
                if (gameEngine.getRollsRemaining() == 3) {   // Before first roll
                    ((JToggleButton) e.getSource()).setSelected(true);  // Keep dice ON
                }
            });
            
            diceToggleButtons[i].setIconTextGap(0);
            diceToggleButtons[i].setContentAreaFilled(false);
            diceToggleButtons[i].setIcon(scaledDiceIcon(unselectedPaths[i], diceSize));
            diceToggleButtons[i].setSelectedIcon(scaledDiceIcon(selectedPaths[i], diceSize));
        }
        
        diceButton1.setPreferredSize(new Dimension(diceSize, diceSize));
        diceButton2.setPreferredSize(new Dimension(diceSize, diceSize));
        diceButton3.setPreferredSize(new Dimension(diceSize, diceSize));
        diceButton4.setPreferredSize(new Dimension(diceSize, diceSize));
        diceButton5.setPreferredSize(new Dimension(diceSize, diceSize));
        diceButton6.setPreferredSize(new Dimension(diceSize, diceSize));
        diceButton.setPreferredSize(new Dimension(3*diceSize,diceSize));
        pauseButton.setPreferredSize(new Dimension(3*diceSize,diceSize));
    }
    public int getStoneSize(){
        return stoneSize;
    }
    protected void disableButtons(){
        for (int i = 0; i < 6; i++){diceToggleButtons[i].setEnabled(false);}
        for (int x = 0; x < 7; x++){
            for (int y = 0; y < 7; y++){
                gridSquares[x][y].tileButton.setEnabled(false);
        }}
        diceButton.setEnabled(false);
    }
    protected void enableButtons(){
        for (int i = 0; i < 6; i++){diceToggleButtons[i].setEnabled(true);}
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++){
                gridSquares[i][j].tileButton.setEnabled(true);
        }}
        diceButton.setEnabled(true);
    }
}
