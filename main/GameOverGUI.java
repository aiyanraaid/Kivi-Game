package main;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;


//import java.awt.event.*;

//import java.awt.event.ActionListener;
public class GameOverGUI extends JPanel
{
    /**
     * settings button -> option to change settings (and color) from main
     *      menu instead of having to load into a game or access game setup first
     * info label -> could be replaced with "choose a button" or something. 
     *      rules will always be accessible from help button.
     */

    private GameEngine gameEngine;
    private ScoreCalculator scoreCalculator;
    private Map<Player, Integer> scores;
    private int winner;
    private int[] finalScores = new int[4];
    private int firstPlace = -1;
    private int secondPlace = -1;
    private int thirdPlace = -1;
    private int fourthPlace = -1;

    // GUI Fields
    private Color[] playerColors = {KiviGame.color.playerColorA, KiviGame.color.playerColorB, KiviGame.color.playerColorC, KiviGame.color.playerColorD};

    private JPanel buttonPanel;
    private JPanel newGamePanel;
    private JPanel exitToMenuPanel;
    private JPanel exitToDesktopPanel;
    private JPanel firstPanel;
    private JPanel secondPanel;
    private JPanel thirdPanel;
    private JPanel fourthPanel;
    private JPanel infoPanel;

    private JLabel first;
    private JLabel second;
    private JLabel third;
    private JLabel fourth;

    private JButton newGameButton;
    private JButton exitToMenuButton;
    private JButton exitToDesktopButton;

    public GameOverGUI(JFrame frame, GameEngine gameEngine){
        this.gameEngine = gameEngine;


        // Leaderboard GUI Aspects
        infoPanel = new JPanel(new GridLayout(4,1));
        firstPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        secondPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        thirdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fourthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        first = new JLabel("");
        second = new JLabel("");
        third = new JLabel("");
        fourth = new JLabel("");
        first.setFont(new Font("Arial", Font.PLAIN, 20));
        second.setFont(new Font("Arial", Font.PLAIN, 20));
        third.setFont(new Font("Arial", Font.PLAIN, 20));
        fourth.setFont(new Font("Arial", Font.PLAIN, 20));


        // Calculating scores and determining leaderboard
        scoreCalculator = new ScoreCalculator(gameEngine);
        scores = scoreCalculator.calculateScores();
        finalScores[0] = scores.getOrDefault(gameEngine.player1, -1);
        finalScores[1] = scores.getOrDefault(gameEngine.player2, -1);
        finalScores[2] = scores.getOrDefault(gameEngine.player3, -1);
        finalScores[3] = scores.getOrDefault(gameEngine.player4, -1);
        generateLeaderboard();

        

        setLayout(new BorderLayout(30, 75));
        //buttons
        buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        newGameButton = new JButton("New Game");
        exitToMenuButton = new JButton("Quit to Main Menu");
        exitToDesktopButton = new JButton("Quit to Desktop");
        
        newGameButton.setBackground(KiviGame.color.buttonColorB);
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 24));
        newGameButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        exitToMenuButton.setBackground(KiviGame.color.buttonColorB);
        exitToMenuButton.setFont(new Font("Arial", Font.PLAIN, 24));
        exitToMenuButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        exitToDesktopButton.setBackground(KiviGame.color.buttonColorB);
        exitToDesktopButton.setFont(new Font("Arial", Font.PLAIN, 24));
        exitToDesktopButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        
        newGamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exitToMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exitToDesktopPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        newGamePanel.add(newGameButton);
        exitToMenuPanel.add(exitToMenuButton);
        exitToDesktopPanel.add(exitToDesktopButton);

        buttonPanel.add(newGamePanel);
        buttonPanel.add(exitToMenuPanel);
        buttonPanel.add(exitToDesktopPanel);
        
        
        newGameButton.addActionListener(e -> {
            frame.setContentPane(new GameSetupGUI(frame));
            frame.revalidate();
            frame.repaint();
        });
        exitToMenuButton.addActionListener(e -> {
            frame.setContentPane(new StartupGUI(frame));
            frame.revalidate();
            frame.repaint();
        });
        exitToDesktopButton.addActionListener(e -> System.exit(0));
        
        
        //labels
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Game Over! Player "+String.valueOf(firstPlace)+" Wins!", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBackground(KiviGame.color.buttonColorA);
        title.setOpaque(true);
        titlePanel.add(title);

        
        JPanel westFill = new JPanel();
        JPanel eastFill = new JPanel();
        add(westFill, BorderLayout.WEST);
        add(eastFill, BorderLayout.EAST);
        add(infoPanel, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    /**
     * All values assigned will be -1 if not valid. -1 used for detection in GUI
     */
    private void generateLeaderboard(){
        
        // Calculate first place
        int player = -1;int temp = -1;
        for (int i = 0; i < 4; i++){
            if (finalScores[i] > temp){
                player = i;
                temp = finalScores[i];
            }
        }
        firstPlace = player;
        first.setText("1st. Player " + String.valueOf(firstPlace+1)+": "+String.valueOf(finalScores[firstPlace])+" points");
        first.setBackground(playerColors[firstPlace]);
        infoPanel.add(first);

        // Calculate second place
        player = -1;temp = -1;
        for (int i = 0; i < 4; i++){
            if (finalScores[i] > temp && i != firstPlace){
                player = i; temp = finalScores[i];
            }
        }
        secondPlace = player;
        second.setText("2nd. Player " + String.valueOf(secondPlace+1)+": "+String.valueOf(finalScores[secondPlace])+" points");
        second.setBackground(playerColors[secondPlace]);
        infoPanel.add(second);

        // Calculate third place
        player = -1;temp = -1;
        for (int i = 0; i < 4; i++){
            if (finalScores[i] > temp && i != firstPlace && i != secondPlace){
                player = i; temp = finalScores[i];
            }
        }
        if (player > -1){ // If there was another positive score (more than 2 players)
            if (finalScores[player] > -1){ // Kept separate to prevent out of bounds error
                thirdPlace = player;
                third.setText("3rd. Player " + String.valueOf(thirdPlace+1)+": "+String.valueOf(finalScores[thirdPlace])+" points");
                third.setBackground(playerColors[thirdPlace]);
                infoPanel.add(third);

                //Calculate fourth place
                player = -1; temp = -1;
                for (int i = 0; i < 4; i++){
                    if (finalScores[i] > temp && i != firstPlace && i!= secondPlace && i != thirdPlace){
                        player = i; temp = finalScores[i];
                    }
                }
                if (player > -1){ // Fourth player detection
                    if (finalScores[player] > -1){ // Separate to prevent out of bounds exception
                        fourthPlace = player;
                        fourth.setText("4th. Player " + String.valueOf(fourthPlace+1)+": "+String.valueOf(finalScores[fourthPlace])+" points");
                        fourth.setBackground(playerColors[fourthPlace]);
                        infoPanel.add(fourth);
                    }
                }
            }   
        }
    }
}