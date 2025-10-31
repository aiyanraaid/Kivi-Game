package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
//import java.awt.event.*;
//import java.awt.event.ActionListener;
public class LoadGameGUI extends JPanel
{
    /**
     * add option to delete saves
     * selecting save sets a text panel to display the date it was saved
     * press load button to load into a selected save
     * press delete button to delete selected save (popup window asking to confirm)
     * empty saves are declared as empty
     */
    private String selectedSavePath = null;
    private JButton loadButton;
    private JFrame frame;
    private JLabel selectedInfo;
    public LoadGameGUI(JFrame frame){
        this.frame = frame;
        setLayout(new BorderLayout(40,50));
        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Kivi - Load Menu");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        titlePanel.setBackground(KiviGame.color.buttonColorA);
        titlePanel.add(title);
        
        //Instruction Label
        JPanel instructionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel instructionLabel = new JLabel("Select a game to load", JLabel.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        instructionLabel.setBackground(KiviGame.color.buttonColorA);
        instructionLabel.setOpaque(true);
        instructionLabel.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*4/5, KiviGame.settings.screenHeight/9));
        instructionPanel.add(instructionLabel);
        
        //Load Buttons
        JPanel saveButtons = new JPanel(new GridLayout(1,3,30,30));
        JButton saveButton1 = new JButton("Save 1");
        JButton saveButton3 = new JButton("Save 3");
        JButton saveButton2 = new JButton("Save 2");
        saveButton1.setBackground(KiviGame.color.buttonColorB);
        saveButton1.setFont(new Font("Arial", Font.PLAIN, 24));
        saveButton1.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        saveButton2.setBackground(KiviGame.color.buttonColorB);
        saveButton2.setFont(new Font("Arial", Font.PLAIN, 24));
        saveButton2.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        saveButton3.setBackground(KiviGame.color.buttonColorB);
        saveButton3.setFont(new Font("Arial", Font.PLAIN, 24));
        saveButton3.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        saveButtons.add(saveButton1);
        saveButtons.add(saveButton2);
        saveButtons.add(saveButton3);
        
        // Selected Save Information
        JPanel selectedInfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        selectedInfo = new JLabel("Selected File: <None>", JLabel.CENTER);
        selectedInfo.setFont(new Font("Arial", Font.BOLD, 24));
        selectedInfo.setBackground(KiviGame.color.buttonColorA);
        selectedInfo.setOpaque(true);
        selectedInfo.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*4/5, KiviGame.settings.screenHeight/9));
        selectedInfoPanel.add(selectedInfo);
        
        // Load, Delete, Back Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1,3,30,30));
        JButton backButton = new JButton("Back");
        loadButton = new JButton("Load");
        JButton deleteButton = new JButton("Delete");
        backButton.addActionListener(e -> {
            frame.setContentPane(new StartupGUI(frame));
            frame.revalidate();
            frame.repaint();});
        backButton.setBackground(KiviGame.color.buttonColorB);
        backButton.setFont(new Font("Arial", Font.PLAIN, 24));
        backButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        loadButton.setBackground(KiviGame.color.buttonColorB);
        loadButton.setFont(new Font("Arial", Font.PLAIN, 24));
        loadButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        loadButton.setEnabled(false); //Set false as default, updated once valid save file is chosen
        deleteButton.setBackground(KiviGame.color.buttonColorB);
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 24));
        deleteButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(loadButton);
        
        // Save Button Functionality
        saveButton1.addActionListener(e -> updateSelectedSave("save_1.dat", "Selected File: <Save 1>"));
        saveButton2.addActionListener(e -> updateSelectedSave("save_2.dat", "Selected File: <Save 2>"));
        saveButton3.addActionListener(e -> updateSelectedSave("save_3.dat", "Selected File: <Save 3>"));
        
        // Load Button Functionality
        loadButton.addActionListener(e -> loadGame(selectedSavePath));

        // Delete Button Functionality
        deleteButton.addActionListener(e -> {
            boolean a = deleteSave(selectedSavePath);
            if (a){System.out.println("delete successful");
            revalidate();
            repaint();}
        });

        // Center Panel Construction
        JPanel centerPanel = new JPanel(new GridLayout(4,1,30,30));
        centerPanel.add(instructionPanel);
        centerPanel.add(saveButtons);
        centerPanel.add(selectedInfoPanel);
        centerPanel.add(buttonPanel);
        
        // Main Content Pane Construction
        JPanel bottomFill = new JPanel();
        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomFill, BorderLayout.SOUTH);
    }

    // Responsible for updating text displaying what save is selected and determining if it is valid to load.
    // Also updates selectedSavePath used for loading/deleting.
    private void updateSelectedSave(String path, String message){
        File file = new File("data/saves/"+path);
        if (file.exists()){
            selectedInfo.setText(message);
            selectedSavePath = path;
            loadButton.setEnabled(true);
        }
        else {
            selectedInfo.setText(message+" <empty>");
            selectedSavePath = null;
            loadButton.setEnabled(false);
        }
        revalidate();
        repaint();
    }

    private boolean deleteSave(String filePath){
        String message = selectedInfo.getText();
        File delFile = new File("data/saves/"+filePath);
        if (delFile.exists()){
            System.out.println("deleting file");
            selectedInfo.setText("Deleting...");
            Timer delTimer = new Timer(1800, e -> {
                updateSelectedSave(filePath, message);
            });
            delTimer.setRepeats(false);
            delTimer.start();
            return delFile.delete();}
        else{System.out.println("delete failed");return false;}
    }

    private void loadGame(String filePath){
        if (filePath != null){
            GameEngine gameEngine = data.SaveLoad.loadGame(filePath);
            gameEngine.frame = frame;
            gameEngine.game.frame = frame;
            frame.setContentPane(gameEngine.game);
            gameEngine.game.restoreTransientFields();
            frame.revalidate();
            frame.repaint();
        }
    }
}
