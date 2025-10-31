package main;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.ActionListener;
public class GameSetupGUI extends JPanel
{
    /**
     * single difficulty button? Or different for each?
     * make difficulty button disappear when no computer player selected
     *      (implement in togglePlayer() method)
     * player state -> efficiency. Initializes players at game start
     *      instead of creating/deleting player objects in menu
     */
    private JPanel topPanel, bottomPanel, middlePanel, leftPanel, rightPanel, labelPanel; //panels 
    private JButton opp1, opp2, opp3, opp4;     //opponent button, when pressed switches from computer to human
    private JLabel gameSetup, oppSlots, infoLabel;  //information labels
    private PlayerState player2, player3, player4;
    public GameSetupGUI(JFrame frame){
        player2 = PlayerState.NONE;
        player3 = PlayerState.NONE;
        player4 = PlayerState.NONE;
        
        //cole code
        topPanel = new JPanel(new GridLayout(2,1));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(KiviGame.color.buttonColorA);
        topPanel.add(titlePanel);
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoLabel = new JLabel("");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(infoLabel);
        topPanel.add(infoPanel);
        middlePanel = new JPanel();
        bottomPanel = new JPanel(new GridLayout(1,4,20,20));
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        //c
        middlePanel.setLayout(new GridLayout(4,1,20,20));
        bottomPanel.setLayout(new FlowLayout());
        
        opp1 = new JButton("Player 1: READY");
        opp2 = new JButton("Player 2: Add Player");
        opp3 = new JButton("Player 3: Add Player");
        opp4 = new JButton("Player 4: Add Player");
        opp1.setFont(new Font("Arial", Font.BOLD, 22));
        opp1.setBackground(KiviGame.color.playerColorA);
        opp1.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        opp2.setFont(new Font("Arial", Font.BOLD, 22));
        opp2.setBackground(KiviGame.color.playerColorB);
        opp2.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        opp3.setFont(new Font("Arial", Font.BOLD, 22));
        opp3.setBackground(KiviGame.color.playerColorC);
        opp3.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        opp4.setFont(new Font("Arial", Font.BOLD, 22));
        opp4.setBackground(KiviGame.color.playerColorD);
        opp4.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        //intiallizing toggle buttons
       
        
        gameSetup = new JLabel("Game Setup Settings");
        gameSetup.setFont(new Font("Arial", Font.BOLD, 26));
        titlePanel.add(gameSetup);
        
        //middlePanel buttons
        middlePanel.add(opp1);
        middlePanel.add(opp2);
        middlePanel.add(opp3);
        middlePanel.add(opp4);
        
        JButton settingsButton = new JButton("Settings");
        JButton backButton = new JButton("Back");
        JButton helpButton = new JButton("Help");
        JButton startGameButton = new JButton("Start");
        //
        backButton.addActionListener(e -> {
            frame.setContentPane(new StartupGUI(frame));
            frame.validate();
            frame.repaint();
        });
        settingsButton.addActionListener(e -> {
            frame.setContentPane(new SettingsGUI(frame, this));
            frame.validate();
            frame.repaint();
        });
        helpButton.addActionListener(e -> {
            frame.setContentPane(new HelpGUI(frame, this));
            frame.validate();
            frame.repaint();
        });
        
        //opp1 -opp3 actionlisteners
        //changing to opp4 from opp1
        opp2.addActionListener(e -> {createPopupMenu(opp2,0).show(opp2,0, opp2.getHeight());});
        opp3.addActionListener(e -> {createPopupMenu(opp3,1).show(opp3,0, opp3.getHeight());}); 
        opp4.addActionListener(e -> {createPopupMenu(opp4,2).show(opp4,0, opp4.getHeight());});
    
      
        
     
        
        startGameButton.addActionListener(e -> {
            if (player2 != PlayerState.NONE || player3 != PlayerState.NONE || player4 != PlayerState.NONE){
                GameEngine game = new GameEngine(frame, player2, player3, player4);
            }
            else{
                infoLabel.setText("At least 2 players are required to start...");
                revalidate();
                repaint();
            }
        });
    
        
        
        setLayout(new BorderLayout(20,100));
        add(middlePanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        startGameButton.setFont(new Font("Arial", Font.BOLD, 20));
        startGameButton.setBackground(KiviGame.color.buttonColorB);
        startGameButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/16, KiviGame.settings.screenHeight/12));
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(KiviGame.color.buttonColorB);
        backButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/16, KiviGame.settings.screenHeight/12));
        settingsButton.setFont(new Font("Arial", Font.BOLD, 20));
        settingsButton.setBackground(KiviGame.color.buttonColorB);
        settingsButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/16, KiviGame.settings.screenHeight/12));
        helpButton.setFont(new Font("Arial", Font.BOLD, 20));
        helpButton.setBackground(KiviGame.color.buttonColorB);
        helpButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/16, KiviGame.settings.screenHeight/12));
        
        JPanel backButtonPanel = new JPanel(new FlowLayout());
        JPanel settingsButtonPanel = new JPanel(new FlowLayout());
        JPanel helpButtonPanel = new JPanel(new FlowLayout());
        JPanel startGameButtonPanel = new JPanel(new FlowLayout());
        backButtonPanel.add(backButton);
        settingsButtonPanel.add(settingsButton);
        
        startGameButtonPanel.add(startGameButton);

        helpButtonPanel.add(helpButton);
        bottomPanel.add(backButtonPanel);
        bottomPanel.add(settingsButtonPanel);
        
        bottomPanel.add(startGameButtonPanel);
        bottomPanel.add(helpButtonPanel);
    }
    
    private JPopupMenu createPopupMenu(JButton oppButton, int index){
        //creating popupmenu 
        JPopupMenu popupMenu = new JPopupMenu();
        //creating menu option
        JMenuItem changeDiff = new JMenuItem("Change Difficulty");
        changeDiff.setBackground(KiviGame.color.boardGridColorB);
        changeDiff.setFont(new Font("Arial", Font.PLAIN, 20)); 
        JMenuItem human = new JMenuItem("Human Player");
        human.setBackground(KiviGame.color.boardGridColorB);
        human.setFont(new Font("Arial", Font.PLAIN, 20));

        
        JMenuItem notPlaying = new JMenuItem("Remove Player");
        notPlaying.setBackground(KiviGame.color.boardGridColorB);
        notPlaying.setFont(new Font("Arial", Font.PLAIN, 20)); 
        
        
        
        JMenuItem computer = new JMenuItem("Computer Player");

        popupMenu.setBackground(KiviGame.color.boardGridColorB);
        computer.setFont(new Font("Arial", Font.PLAIN, 20));
        computer.setBackground(KiviGame.color.boardGridColorB);
    
        changeDiff.addActionListener(e ->{
                                    if(index == 0){
                                        if(player2 == PlayerState.COMPUTER_HARD){
                                            player2 = PlayerState.COMPUTER_EASY;
                                            updatePlayerSlot(oppButton,index,"<AI EASY> READY");
                                        }else{
                                            player2 = PlayerState.COMPUTER_HARD;
                                            updatePlayerSlot(oppButton,index,"<AI HARD> READY");
                                        }
                                    }
                                    else if(index == 1){
                                        if(player3 == PlayerState.COMPUTER_HARD){
                                            player3 = PlayerState.COMPUTER_EASY;
                                            updatePlayerSlot(oppButton,index,"<AI EASY> READY");
                                        }else{
                                            player3 = PlayerState.COMPUTER_HARD;
                                            updatePlayerSlot(oppButton,index,"<AI HARD> READY");
                                        }
                                    }
                                    else{
                                        if(player4 == PlayerState.COMPUTER_HARD){
                                            player4 = PlayerState.COMPUTER_EASY;
                                            updatePlayerSlot(oppButton,index,"<AI EASY> READY");
                                        }else{
                                            player4 = PlayerState.COMPUTER_HARD;
                                            updatePlayerSlot(oppButton,index,"<AI HARD> READY");
                                        }
                                    }
                                    });
        human.addActionListener(e ->{
                                    if(index == 0){
                                        player2 = PlayerState.HUMAN;
                                    }
                                    else if(index == 1){
                                        player3 = PlayerState.HUMAN;
                                    }
                                    else{
                                        player4 = PlayerState.HUMAN;
                                    }
                                    updatePlayerSlot(oppButton,index,"READY");
                                    });
        notPlaying.addActionListener(e ->{
                                    if(index == 0){
                                        player2 = PlayerState.NONE;
                                    }
                                    else if(index == 1){
                                        player3 = PlayerState.NONE;
                                    }
                                    else{
                                        player4 = PlayerState.NONE;
                                    }
                                    updatePlayerSlot(oppButton,index,"Add Player");
                                    });
        computer.addActionListener(e -> {
                                    if(index == 0){
                                        player2 = PlayerState.COMPUTER_EASY;
                                    }
                                    else if(index == 1){
                                        player3 = PlayerState.COMPUTER_EASY;
                                    }
                                    else{
                                        player4 = PlayerState.COMPUTER_EASY;
                                    }
                                    updatePlayerSlot(oppButton, index, "<AI EASY> READY");});
        
        
        if(oppButton.getText().contains("<AI HARD>") || oppButton.getText().contains("<AI EASY>")){
            popupMenu.add(notPlaying);
            popupMenu.add(changeDiff);
        }
        else if(oppButton.getText().contains("Add Player")){
            popupMenu.add(human);
            popupMenu.add(computer);
        }else{
            popupMenu.add(notPlaying);
        }
    
        //returning created popupmenu
        return popupMenu;
    }
    private String getSlotText(JButton oppButton, String player){
        if (oppButton == opp2) return "Player 2: " + player;
        if (oppButton == opp3) return "Player 3: " + player;
        if (oppButton == opp4) return "Player 4: " + player;
        return " " + player;
    }
    
    
    
    private void updatePlayerSlot(JButton oppButton, int index, String player){
        oppButton.setText(getSlotText(oppButton,player));
    }
    

}