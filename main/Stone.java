package main;

import java.awt.*;
import javax.swing.*;

/**
 * Stone is a simple class extending JPanel. It is used to illustrate a game token
 * in the form of a small circle that overlays the JLayeredPane extension class
 * {@see GridSquare}. It is solely for visual goals.
 */
public class Stone extends JPanel
{
    private Color color;
    private int diameter;
    public Stone(Color color, int diameter){
        this.color = color;
        this.diameter = diameter;
        setPreferredSize(new Dimension(diameter, diameter));
        setOpaque(false);
    }
    
    public void paintComponent(Graphics tile){
        super.paintComponent(tile);
        Graphics2D tile2d = (Graphics2D) tile;
        tile2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        tile2d.setColor(color);
        tile2d.fillOval(0,0,diameter,diameter);
    }
    
    public void setColor(Color color){this.color = color;}
}
