package main;

import java.awt.Color;

public class ColorTheme

{
    /**
     * make sure only one instance of ColourTheme exists at once
     * launch thing to keep same colour settings? overload constructor?
     * 
     * blue-yellow colourblind -> blue/pink theme, dont use yellows or greens
     * other 2 colorblinds -> avoid greens, reds,
     */
    private static final Color NORMAL_BUTTON_A = new Color(255, 220, 61); // yellow
    private static final Color NORMAL_BUTTON_HOVER_A = new Color(255, 172, 59);// dark yellow
    private static final Color NORMAL_BUTTON_B = new Color(0, 194, 249);// blue
    private static final Color NORMAL_BUTTON_HOVER_B = new Color(0, 121, 250);// dark blue
    private static final Color NORMAL_BOARD_A = new Color(255, 157, 200);// pink
    private static final Color NORMAL_BOARD_B = new Color(0, 235, 193);// opal
    private static final Color NORMAL_BOARD_C = Color.WHITE;
    private static final Color NORMAL_PLAYER_A = new Color(246, 2, 45);// red
    private static final Color NORMAL_PLAYER_B = new Color(0, 180, 8);// green
    private static final Color NORMAL_PLAYER_C = new Color(0, 121, 250);// blue
    private static final Color NORMAL_PLAYER_D = new Color(167, 0, 252);// purple

    private static final Color CORRECT_BUTTON_A = new Color(255, 110, 58);// orange
    private static final Color CORRECT_BUTTON_HOVER_A = new Color(255, 116, 81);
    private static final Color CORRECT_BUTTON_B = new Color(0, 194, 249);// blue
    private static final Color CORRECT_BUTTON_HOVER_B = new Color(0, 141, 249);
    private static final Color CORRECT_BOARD_A = new Color(226, 1, 52); // red
    private static final Color CORRECT_BOARD_B = new Color(0, 252, 207); // blue
    private static final Color CORRECT_BOARD_C = Color.WHITE;
    private static final Color CORRECT_PLAYER_A = new Color(246, 2, 45);// red
    private static final Color CORRECT_PLAYER_B = new Color(255, 178, 253);// pink
    private static final Color CORRECT_PLAYER_C = new Color(0, 159, 129);// auqamarine kind of but grey for colorblind
    private static final Color CORRECT_PLAYER_D = new Color(132, 0, 205);// purple dark

    public Color buttonColorA;
    public static Color buttonHoverColorA;
    public static Color buttonColorB;
    public static Color buttonHoverColorB;
    public static Color textColor; // neccessary?
    public static Color boardGridColorA;
    public static Color boardGridColorB;
    public static Color boardGridColorC;
    public static Color playerColorA;
    public static Color playerColorB;
    public static Color playerColorC;
    public static Color playerColorD;

    public static boolean isColorCorrected;

    public ColorTheme() {
        isColorCorrected = false;
        buttonColorA = NORMAL_BUTTON_A;
        buttonHoverColorA = NORMAL_BUTTON_HOVER_A;
        buttonColorB = NORMAL_BUTTON_B;
        buttonHoverColorB = NORMAL_BUTTON_HOVER_B;
        boardGridColorA = NORMAL_BOARD_A;
        boardGridColorB = NORMAL_BOARD_B;
        boardGridColorC = NORMAL_BOARD_C;
        playerColorA = NORMAL_PLAYER_A;
        playerColorB = NORMAL_PLAYER_B;
        playerColorC = NORMAL_PLAYER_C;
        playerColorD = NORMAL_PLAYER_D;
    }

    public void toggleColorCorrection() {
        if (isColorCorrected == true) {
            buttonColorA = NORMAL_BUTTON_A;
            buttonHoverColorA = NORMAL_BUTTON_HOVER_A;
            buttonColorB = NORMAL_BUTTON_B;
            buttonHoverColorB = NORMAL_BUTTON_HOVER_B;
            boardGridColorA = NORMAL_BOARD_A;
            boardGridColorB = NORMAL_BOARD_B;
            boardGridColorC = NORMAL_BOARD_C;
            playerColorA = NORMAL_PLAYER_A;
            playerColorB = NORMAL_PLAYER_B;
            playerColorC = NORMAL_PLAYER_C;
            playerColorD = NORMAL_PLAYER_D;
            isColorCorrected = false;
        } else if (isColorCorrected == false) {
            buttonColorA = CORRECT_BUTTON_A;
            buttonHoverColorA = CORRECT_BUTTON_HOVER_A;
            buttonColorB = CORRECT_BUTTON_B;
            buttonHoverColorB = CORRECT_BUTTON_HOVER_B;
            boardGridColorA = CORRECT_BOARD_A;
            boardGridColorB = CORRECT_BOARD_B;
            boardGridColorC = CORRECT_BOARD_C;
            playerColorA = CORRECT_PLAYER_A;
            playerColorB = CORRECT_PLAYER_B;
            playerColorC = CORRECT_PLAYER_C;
            playerColorD = CORRECT_PLAYER_D;
            isColorCorrected = true;
        }
    }
}
