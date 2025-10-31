package main;

/**
 * Just an enum to store values of dice combos. Can be
 * deleted depending on how the score calculator is implemented.
 */
public enum DiceCombination {
    TWO_PAIRS(1),
    THREE_OF_A_KIND(1),
    LITTLE_STRAIGHT(1),
    FULL_HOUSE(1),
    FOUR_OF_A_KIND(2),
    LARGE_STRAIGHT(2),
    ALL_EVEN(2),
    ALL_ODD(2),
    TWELVE_OR_FEWER(2),
    THIRTY_OR_MORE(2),
    THREE_PAIRS(3),
    TWO_TIMES_THREE_OF_A_KIND(3),
    FOUR_OF_A_KIND_AND_A_PAIR(3);

    private int points;

    DiceCombination(int points) {
        this.points = points;
    }

    public int getScore() {
        return points;
    }
}
