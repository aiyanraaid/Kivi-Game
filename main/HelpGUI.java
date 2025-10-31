package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
public class HelpGUI extends JPanel
{
    /**
     * Please note, HelpGUI is a placeholder for a future, more detailed menu.
     * The purpose of this GUI is solely for the aesthetic of the prototype.
     */
    public HelpGUI(JFrame frame, JPanel previousFrame){
        // Back Button
        JButton backButton = new JButton("Back");
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton.setBackground(KiviGame.color.buttonColorB);
        backButton.setFont(new Font("Arial", Font.PLAIN, 22));
        backPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.setContentPane(previousFrame);
            frame.validate();
            frame.repaint();
        });
        
        // Title
        JLabel title = new JLabel("Kivi - Help", JLabel.CENTER);
        JPanel titlePanel = new JPanel();
        titlePanel.add(title);
        titlePanel.setBackground(KiviGame.color.buttonColorA);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        
        // Information (need to replace jlabels with formatted text areas)
        JLabel combo = new JLabel("Combinations");
    
        JLabel rules = new JLabel(" Rules");
        rules.setFont(new Font("Arial", Font.PLAIN, 50));
        rules.setBackground(KiviGame.color.buttonColorB);
        combo.setFont(new Font("Arial", Font.PLAIN, 50));
        combo.setBackground(KiviGame.color.buttonColorB);
        
        JPanel infoPane = new JPanel();
        JPanel infoLeft = new JPanel();
        JPanel infoRight = new JPanel();
        infoPane.add(infoLeft);
        infoPane.add(infoRight);
        infoPane.setLayout(new GridLayout(1,2));
        infoLeft.setLayout(new BoxLayout(infoLeft, BoxLayout.Y_AXIS));
        infoRight.setLayout(new BoxLayout(infoRight, BoxLayout.Y_AXIS));
        
        //new GridLayout(2,1)
        String text = """
               - Every player starts with 10 stones each
               
               - During a player's turn they must roll 6 dice 
                 and can elect to do two full or partial rethrows
               
               - Based upon the combination the player's dice 
                 forms they may place a stone on a 
                 corresponding square
               
               - If the player has no valid combination, 
                 their stone is forfeited
               
               - Placing a stone on a pink square is 3 points, 
                 placing a stone on a black square is 2 points, 
                 placing a stone on a white square is 1 point
                 
               - If a player rolls a special combination
                 Five of a Kind or Complete Straight they 
                 may place their stone on any free square
                 
               - If a player rolls a special combination
                 Six of a Kind, then the player may place
                 their stone on any square, if they choose 
                 a square that's occupied they must move 
                 said stone to another available square. 
                 If no square available the stone is 
                 forfeited. 
               
               - The scores are calculated at the end of the 
                 game. They are calculated by rows and columns.
                 The player's stones that form a continous 
                 row/column are added together and multiplied
                 by the length of the row/column. An isolated 
                 stone counts as a row of 1. All the stones 
                 points are added together and that is the
                 player's final score.
                 
               - The game lasts a total of ten rounds 
                 and whoever has the most points at the
                 end is deemed the winner
               """;
               
        String text2 = """
                - Two pair = 2 pairs of dice 
                  1 point
                
                - Three of a Kind = 3 dice of the same value
                  1 point
                
                - Little Straight = 4 consecutive values of dice
                  1 point 
                
                - Full House = Three of a kind and two pair
                  1 point
                
                - Four of a Kind = 4 dice of the same value
                  2 points
                
                - Large Straight = 5 consecutive values of dice
                  2 points 
                
                - All Even = Each dice is of even value
                  2 points
                
                - All Odd = Each dice is of odd value
                  2 points
                
                - 12 or Fewer = The sum of the values is 12 or 
                                less
                  2 points 
                  
                - 30 or More = The sum of the values is 30 or 
                               more
                  2 points 
                  
                - Three pairs = 3 pairs of dice 
                  3 points 
                  
                - Two Times Three of a Kind = Two sets of 3 
                                              of a kind
                  3 points
                  
                - Four of a Kind and a Pair = One 4 of a kind 
                                              and a pair
                  3 points                 
                  
                  *Special Combinations*
                
                - Five of a Kind = 5 dice of the same value
                  Any free square
                
                - Complete Straight = 6 consecutive values 
                                     of dice
                  Any free square
                
                - Six of a Kind = 6 dice of the same value
                  *Any Square*
                """;
        
        JTextArea textArea = new JTextArea(text);
        JTextArea textArea2 = new JTextArea(text2);
        
        textArea.setFont(new Font("Arial",Font.PLAIN,15));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(false);
        textArea.setEditable(false);
        
        textArea2.setFont(new Font("Arial",Font.PLAIN,15));
        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(false);
        textArea2.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        
        infoLeft.add(rules); 
        infoLeft.add(Box.createVerticalStrut(20));
        infoRight.add(combo);
        infoRight.add(Box.createVerticalStrut(20));
        infoLeft.add(scrollPane);
        infoRight.add(scrollPane2);
        
        
        
        
        
      
        
       
        
        setLayout(new BorderLayout(30,50));
        JPanel fillWest = new JPanel();
        JPanel fillEast = new JPanel();
        add(fillWest, BorderLayout.WEST);
        add(fillEast, BorderLayout.EAST);
        
        add(titlePanel, BorderLayout.NORTH);
        add(backPanel, BorderLayout.SOUTH);
        add(infoPane,BorderLayout.CENTER);
        
    }
}