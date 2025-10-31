package main;

//import java.awt.*;
import javax.swing.*;
public class SettingsManager
{
    public static int screenHeight;
    public static int screenWidth;
    public static boolean autosave;
    
    public SettingsManager(){
        autosave = false;
        screenHeight = 600;
        screenWidth = 800;
    }
    public void changeResolution(int width, int height, JFrame frame){
        screenHeight = height;
        screenWidth = width;
        frame.setSize(screenWidth, screenHeight);
        frame.revalidate();
        frame.repaint();
    }
}
