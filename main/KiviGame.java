package main;


import javax.swing.*;
import java.awt.*;
public class KiviGame
{
    public static ColorTheme color;//initialized once, all colours are static
    public static  SettingsManager settings;
    public static DiceCombination[][] combos;
    public static CombinationMatcher combinationMatcher;
    public KiviGame()
    {
        /**
         * switch frames in GUI classes or here? here with enum?
         */
        color = new ColorTheme();
        settings = new SettingsManager();
        combos = generateCombos();
        combinationMatcher = new CombinationMatcher();
        JFrame frame = new JFrame("Kivi Proof of Concept Prototype");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(settings.screenWidth, settings.screenHeight); //change later
        frame.setContentPane(new StartupGUI(frame));
        frame.setResizable(false);
        frame.setVisible(true);
    }
    /**
     * A private method called once when the game is launched. It initializes
     * the patterns and values of tiles on the game board {@see DiceCombination}
     */
    private DiceCombination[][] generateCombos(){
        DiceCombination[][] combinations = {
        {DiceCombination.TWO_PAIRS, DiceCombination.ALL_EVEN, DiceCombination.LITTLE_STRAIGHT, DiceCombination.TWELVE_OR_FEWER, DiceCombination.THREE_OF_A_KIND, DiceCombination.ALL_ODD, DiceCombination.LITTLE_STRAIGHT},
        {DiceCombination.LARGE_STRAIGHT, DiceCombination.FOUR_OF_A_KIND_AND_A_PAIR, DiceCombination.FOUR_OF_A_KIND, DiceCombination.FULL_HOUSE, DiceCombination.LARGE_STRAIGHT, DiceCombination.TWO_TIMES_THREE_OF_A_KIND, DiceCombination.THIRTY_OR_MORE},
        {DiceCombination.TWELVE_OR_FEWER, DiceCombination.THREE_OF_A_KIND, DiceCombination.THIRTY_OR_MORE, DiceCombination.ALL_EVEN, DiceCombination.THREE_PAIRS, DiceCombination.LITTLE_STRAIGHT, DiceCombination.FOUR_OF_A_KIND},
        {DiceCombination.THREE_OF_A_KIND, DiceCombination.THREE_PAIRS, DiceCombination.LARGE_STRAIGHT, DiceCombination.TWO_TIMES_THREE_OF_A_KIND, DiceCombination.ALL_ODD, DiceCombination.FOUR_OF_A_KIND_AND_A_PAIR, DiceCombination.TWO_PAIRS},
        {DiceCombination.ALL_ODD, DiceCombination.LITTLE_STRAIGHT, DiceCombination.FOUR_OF_A_KIND_AND_A_PAIR, DiceCombination.TWELVE_OR_FEWER, DiceCombination.FOUR_OF_A_KIND, DiceCombination.FULL_HOUSE, DiceCombination.ALL_ODD},
        {DiceCombination.ALL_EVEN, DiceCombination.TWO_TIMES_THREE_OF_A_KIND, DiceCombination.ALL_ODD, DiceCombination.TWO_PAIRS, DiceCombination.THIRTY_OR_MORE, DiceCombination.THREE_PAIRS, DiceCombination.FOUR_OF_A_KIND},
        {DiceCombination.THREE_OF_A_KIND, DiceCombination.THIRTY_OR_MORE, DiceCombination.FULL_HOUSE, DiceCombination.LARGE_STRAIGHT, DiceCombination.TWO_PAIRS, DiceCombination.TWELVE_OR_FEWER, DiceCombination.FULL_HOUSE}};
        return combinations;
    }
    public static DiceCombination getCombo(int x, int y){
        return combos[y][x];
    }
}
