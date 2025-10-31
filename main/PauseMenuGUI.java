package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
public class PauseMenuGUI extends JPanel
{
    private JFrame frame;
    private GameEngine gameEngine;
    public PauseMenuGUI(JFrame frame, GameEngine gameEngine){
        this.frame = frame;
        this.gameEngine = gameEngine;
        
        // Panels
        JPanel centerPanel = new JPanel(new GridLayout(2,1,30,100));
        JPanel bottomFill = new JPanel();
        JPanel leftFill = new JPanel();
        JPanel rightFill = new JPanel();
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        
        // Title Pane
        JLabel title = new JLabel("Kivi - Paused", JLabel.CENTER);
        titlePanel.setBackground(KiviGame.color.buttonColorA);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        titlePanel.add(title);
        
        // Buttons & Functionality
        JButton resumeButton = new JButton("Resume");
        JButton saveButton = new JButton("Save Game");
        JButton quitToMenuButton = new JButton("Quit to Main Menu");
        JButton quitToDesktopButton = new JButton("Quit to Desktop");
        JButton settingsButton = new JButton("Settings");
        JButton helpButton = new JButton("Help");
        resumeButton.addActionListener(e -> gameEngine.resumeGame());
        quitToMenuButton.addActionListener(e -> {
            frame.setContentPane(new StartupGUI(frame));
            frame.revalidate();
            frame.repaint();
        });
        quitToDesktopButton.addActionListener(e -> System.exit(0));
        settingsButton.addActionListener(e -> {
            frame.setContentPane(new SettingsGUI(frame, this));
            frame.revalidate();
            frame.repaint();
        });
        helpButton.addActionListener(e -> {
            frame.setContentPane(new HelpGUI(frame, this));
            frame.revalidate();
            frame.repaint();
        });
        
        // Save Button Functionality
        saveButton.addActionListener(e -> {System.out.println("save game called");
        saveGame();});
        
        
        // Button Aesthetics
        resumeButton.setBackground(KiviGame.color.buttonColorB);
        resumeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        resumeButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        quitToMenuButton.setBackground(KiviGame.color.buttonColorB);
        quitToMenuButton.setFont(new Font("Arial", Font.PLAIN, 20));
        quitToMenuButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        saveButton.setBackground(KiviGame.color.buttonColorB);
        saveButton.setFont(new Font("Arial", Font.PLAIN, 20));
        saveButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        quitToDesktopButton.setBackground(KiviGame.color.buttonColorB);
        quitToDesktopButton.setFont(new Font("Arial", Font.PLAIN, 20));
        quitToDesktopButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        settingsButton.setBackground(KiviGame.color.buttonColorB);
        settingsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        settingsButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        helpButton.setBackground(KiviGame.color.buttonColorB);
        helpButton.setFont(new Font("Arial", Font.PLAIN, 20));
        helpButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        
        
        // Top Buttons Panel
        JPanel topButtons = new JPanel(new GridLayout(1,3,30,30));
        topButtons.add(saveButton);
        topButtons.add(settingsButton);
        topButtons.add(helpButton);
        
        // Bottom Buttons Panel
        JPanel bottomButtons = new JPanel(new GridLayout(1,3,30,30));
        bottomButtons.add(resumeButton);
        bottomButtons.add(quitToMenuButton);
        bottomButtons.add(quitToDesktopButton);
        
        //Assemble frame
        setLayout(new BorderLayout(40,50));
        centerPanel.add(topButtons);
        centerPanel.add(bottomButtons);
        add(leftFill, BorderLayout.WEST);
        add(rightFill, BorderLayout.EAST);
        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomFill, BorderLayout.SOUTH);
    }

    private void saveGame(){
        String savePath = gameEngine.getSavePath();
        System.out.println(savePath);
        if (savePath != null){
            System.out.println("savepath not null, attempting save with "+savePath);
            // Updates save of current game if it isn't a new game.
            data.SaveLoad.saveGame(gameEngine, savePath);
        }
        else{
            System.out.println("savepath null");
            String slot = checkSlots();
            System.out.println("checkslots called");
            // Saves to a free save file if one exists.
            if (slot != null){
                System.out.println("checkslots returns not null, attempting save with "+slot);
                gameEngine.setPath(slot);
                data.SaveLoad.saveGame(gameEngine, slot);}
            else{
                // Current game is a new game and there are no free save files. User is asked to overwrite or cancel.
                System.out.println("slot returned null, no saves, calling overwrite pane");
                String choice = overwriteSavePane();
                if (choice != null){data.SaveLoad.saveGame(gameEngine, choice);}
            }
        }
    }

    private String overwriteSavePane(){
        // Options
        String[] saveSlots = {"Save 1", "Save 2", "Save 3", "Cancel"};
        // Generate Option Pane Popup
        int choice = JOptionPane.showOptionDialog(null, "All save slots are in use. Cancel or choose a save to overwrite:", "Save Game", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, saveSlots, saveSlots[3]);
        // User chose to cancel save operation or closed the popup.
        if (choice == 3 || choice == JOptionPane.CLOSED_OPTION){return null;}
        return "save_"+String.valueOf(choice+1)+".dat";
    }

    // Private method used in saveGame() to check if there are any available save slots.
    private String checkSlots(){
        File slot1 = new File("data/saves/save_1.dat");
        File slot2 = new File("data/saves/save_2.dat");
        File slot3 = new File("data/saves/save_3.dat");
        if (!slot1.exists()){return "save_1.dat";}
        else if (!slot2.exists()){return "save_2.dat";}
        else if (!slot3.exists()){return "save_3.dat";}
        else {return null;}
    }
    // Used for restoring a KiviBoard after finishing in the pause menu.
    public void restore(){
        frame.setContentPane(new PauseMenuGUI(frame, gameEngine));
        frame.revalidate();
        frame.repaint();
    }
}