package main;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;

//import java.awt.event.ActionListener;
public class StartupGUI extends JPanel
{
    /**
     * settings button -> option to change settings (and color) from main
     *      menu instead of having to load into a game or access game setup first
     * info label -> could be replaced with "choose a button" or something. 
     *      rules will always be accessible from help button.
     */
    public StartupGUI(JFrame frame){
        setLayout(new BorderLayout(30, 75));
        //buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        JButton newGameButton = new JButton("New Game");
        JButton loadGameButton = new JButton("Load Game");
        JButton exitGameButton = new JButton("Quit to Desktop");
        JButton settingsButton = new JButton("Settings");
        
        newGameButton.setBackground(KiviGame.color.buttonColorB);
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 24));
        newGameButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        loadGameButton.setBackground(KiviGame.color.buttonColorB);
        loadGameButton.setFont(new Font("Arial", Font.PLAIN, 24));
        loadGameButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        exitGameButton.setBackground(KiviGame.color.buttonColorB);
        exitGameButton.setFont(new Font("Arial", Font.PLAIN, 24));
        exitGameButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        settingsButton.setBackground(KiviGame.color.buttonColorB);
        settingsButton.setFont(new Font("Arial", Font.PLAIN, 24));
        settingsButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        
        JPanel newGamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel loadGamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel exitGamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        newGamePanel.add(newGameButton);
        loadGamePanel.add(loadGameButton);
        exitGamePanel.add(exitGameButton);
        settingsPanel.add(settingsButton);
        buttonPanel.add(newGamePanel);
        buttonPanel.add(loadGamePanel);
        buttonPanel.add(settingsPanel);
        buttonPanel.add(exitGamePanel);
        
        
        newGameButton.addActionListener(e -> {
            frame.setContentPane(new GameSetupGUI(frame));
            frame.revalidate();
            frame.repaint();
        });
        loadGameButton.addActionListener(e -> {
            frame.setContentPane(new LoadGameGUI(frame));
            frame.revalidate();
            frame.repaint();
        });
        settingsButton.addActionListener(e -> {
            frame.setContentPane(new SettingsGUI(frame, this));
            frame.revalidate();
            frame.repaint();
        });
        exitGameButton.addActionListener(e -> System.exit(0));
        
        
        //labels
        JLabel title = new JLabel("Kivi - Main Menu", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBackground(KiviGame.color.buttonColorA);
        title.setOpaque(true);
        
        
        JPanel infoPanel = new JPanel(new GridLayout(9,1));
        infoPanel.setBackground(KiviGame.color.buttonColorA);
        JLabel line1 = new JLabel("How to Play Kivi", JLabel.CENTER);
        line1.setFont(new Font("Arial", Font.PLAIN, 24));
        JLabel line2 = new JLabel("▶ During your turn, throw the 6 dice. You may make 2 full or partial rethrows.");
        line2.setFont(new Font("Arial", Font.PLAIN, 18));
        JLabel line3 = new JLabel("▶ Match the face values of the dice to a combination of an available square.");
        line3.setFont(new Font("Arial", Font.PLAIN, 18));
        JLabel line4 = new JLabel("▶ Place a stone on a matching square or forfeit it if there are no matches.");
        line4.setFont(new Font("Arial", Font.PLAIN, 18));
        JLabel line5 = new JLabel("▶ After each player has played 10 turns, calculate the scores according to");
        line5.setFont(new Font("Arial", Font.PLAIN, 18));
        JLabel line6 = new JLabel("  the values of squares beneath a player's stones. Stones in a horizontal or");
        line6.setFont(new Font("Arial", Font.PLAIN, 18));
        JLabel line7 = new JLabel("  vertical line will have their score values summed and multiplied by the length");
        line7.setFont(new Font("Arial", Font.PLAIN, 18));
        JLabel line8 = new JLabel("  of the line.");
        line8.setFont(new Font("Arial", Font.PLAIN, 18));
        
        infoPanel.add(line1);
        infoPanel.add(line2);
        infoPanel.add(line3);
        infoPanel.add(line4);
        infoPanel.add(line5);
        infoPanel.add(line6);
        infoPanel.add(line7);
        infoPanel.add(line8);
        
        JPanel westFill = new JPanel();
        JPanel eastFill = new JPanel();
        add(westFill, BorderLayout.WEST);
        add(eastFill, BorderLayout.EAST);
        add(infoPanel, BorderLayout.CENTER);
        add(title, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        
        
        
    }
}
