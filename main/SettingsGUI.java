package main;

import javax.swing.*;
import java.awt.*;
import javax.swing.JPopupMenu;


//import java.awt.event.*;
//import java.awt.event.ActionListener;
public class SettingsGUI extends JPanel{
    private String selectedResolution = "800 x 600";
    public SettingsGUI(JFrame frame, JPanel previousFrame){
        
      
        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Kivi - Settings Menu", JLabel.CENTER);
        titlePanel.setBackground(KiviGame.color.buttonColorA);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        titlePanel.add(title);
        
        JPanel backContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 22));
        backButton.setBackground(KiviGame.color.buttonColorB);
        backButton.setPreferredSize(new Dimension(KiviGame.settings.screenWidth/7, KiviGame.settings.screenHeight/10));
        backButton.addActionListener(e -> {
            //Generates new instance of previous frame to apply graphical changes.
            // -> Have to implement later a way to return to gameboard from settings menu
            //      that has changes applied without remaking board
            if(previousFrame instanceof StartupGUI){frame.setContentPane(new StartupGUI(frame));}
            else if(previousFrame instanceof GameSetupGUI){frame.setContentPane(new GameSetupGUI(frame));}
            else if(previousFrame instanceof PauseMenuGUI){((PauseMenuGUI)previousFrame).restore();}
            else{frame.setContentPane(previousFrame);}
            frame.revalidate();
            frame.repaint();});
        backContainer.add(backButton);
        
        JPanel toggleColorPanel = new JPanel(new FlowLayout());
        JButton toggleColor = new JButton("Toggle Color Correction");
        toggleColor.setPreferredSize(new Dimension(KiviGame.settings.screenWidth*3/7, KiviGame.settings.screenHeight/10));
        toggleColor.setBackground(KiviGame.color.buttonColorB);
        toggleColor.setFont(new Font("Arial", Font.BOLD, 22));
        toggleColor.addActionListener(e -> {
            //Updates colors for all GUIs, including itself.
            KiviGame.color.toggleColorCorrection();
            frame.setContentPane(new SettingsGUI(frame, previousFrame));
            frame.revalidate();
            frame.repaint();});
        toggleColorPanel.add(toggleColor);

        
        JPanel resolutionPanel = new JPanel(new FlowLayout());
        JLabel resolutionLabel = new JLabel("Change Resolution", JLabel.CENTER);
        JButton changeRes = new JButton("Change Resolution");
        changeRes.setBackground(KiviGame.color.buttonColorA);
        changeRes.setFont(new Font("Arial", Font.PLAIN, 20));
        resolutionLabel.setBackground(KiviGame.color.buttonColorA);
        resolutionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        
    
        changeRes.addActionListener(e -> createPopUpMenu().show(changeRes,0,changeRes.getHeight()));
        
        
        
        JPanel resolutionSubPanel = new JPanel(new GridLayout(1,2,30,30));
        
        JButton resolutionConfirmButton = new JButton("Confirm");
        resolutionConfirmButton.setPreferredSize(new Dimension(50,50));
        resolutionConfirmButton.setBackground(KiviGame.color.buttonColorA);
        resolutionConfirmButton.setFont(new Font("Arial", Font.PLAIN, 20));
        resolutionConfirmButton.addActionListener(e -> {
    
            if(selectedResolution.equals("800 x 600")){
                KiviGame.settings.changeResolution(800, 600, frame);
            }
            else if(selectedResolution.equals("1000 x 800")){
                KiviGame.settings.changeResolution(1000, 800,frame);
            }
            else if(selectedResolution.equals("1920 x 1080")){
                KiviGame.settings.changeResolution(1920, 1080, frame);
            }
            revalidate();
            repaint();
        });
        resolutionPanel.add(resolutionLabel);
        JPanel resolutionMasterPanel = new JPanel(new GridLayout(1,1,30,30));
        //resolutionMasterPanel.add(popupMenu);
        resolutionSubPanel.add(changeRes);
        resolutionSubPanel.add(resolutionConfirmButton);
        resolutionMasterPanel.add(resolutionSubPanel);
        
        // Grid will change size later for more settings
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.add(toggleColorPanel);
        settingsPanel.add(resolutionMasterPanel);
        
        
        setLayout(new BorderLayout(40,80));
        add(titlePanel, BorderLayout.NORTH);
        add(settingsPanel, BorderLayout.CENTER);
        add(backContainer, BorderLayout.SOUTH);
    }
    private JPopupMenu createPopUpMenu(){
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem res1 = new JMenuItem("800 x 600 ");
        JMenuItem res2 = new JMenuItem("1000 x 800");
        JMenuItem res3 = new JMenuItem("1920 x 1080");
        
        res1.setBackground(KiviGame.color.boardGridColorB);
        res1.setFont(new Font("Arial", Font.PLAIN, 20));
        res2.setBackground(KiviGame.color.boardGridColorB);
        res2.setFont(new Font("Arial", Font.PLAIN, 20));
        res3.setBackground(KiviGame.color.boardGridColorB);
        res3.setFont(new Font("Arial", Font.PLAIN, 20));
        
        res1.addActionListener(e -> selectedResolution = "800 x 600");
        res2.addActionListener(e -> selectedResolution = "1000 x 800");
        res3.addActionListener(e -> selectedResolution = "1920 x 1080");
        popupMenu.add(res1);
        popupMenu.add(res2);
        popupMenu.add(res3);
        
        return popupMenu;
    }
}
