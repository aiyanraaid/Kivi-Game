package main;

import java.util.*;

public class CombinationMatcher {

    /**
     * Can also take an array instead
     * 
     * Main goal is to just use conditionals/regex to match dice combos to
     * all the combos listed in KiviGame. int a-f are random numbers passed in
     * 
     * does nothing except map dice values to combinations. Combinations sent back
     * to caller.
     * 
     * returns any matching combos (can be multiple, match multiple tiles)
     */
    private Map<Integer, Integer> frequencyMap;
    private int a, b, c, d, e, f;
    private ArrayList<DiceCombination> validCombos;

    public ArrayList CombinationMatcher(int[] vals) {
        a = vals[0];
        b = vals[1];
        c = vals[2];
        d = vals[3];
        e = vals[4];
        f = vals[5];
        int pairCount = 0;
        int evenCount = 0;
        int oddCount = 0;
        int threeOfKCount = 0;
        validCombos = new ArrayList<DiceCombination>();
        frequencyMap = new HashMap<>();
        int[] dice = { a, b, c, d, e, f };
        for (int die : dice) {
            frequencyMap.put(die, frequencyMap.getOrDefault(die, 0) + 1);
        }
        Collection<Integer> justFrequencies = frequencyMap.values();

        // checking combos
        if (justFrequencies.contains(4))
            validCombos.add(DiceCombination.FOUR_OF_A_KIND); // four of a kind
        if (justFrequencies.contains(3) && justFrequencies.contains(2))
            validCombos.add(DiceCombination.FULL_HOUSE); // Full house
        if (justFrequencies.contains(4) && justFrequencies.contains(2))
            validCombos.add(DiceCombination.FOUR_OF_A_KIND_AND_A_PAIR); // four of a kind + pair

        if (a + b + c + d + e + f > 30) {
            validCombos.add(DiceCombination.THIRTY_OR_MORE); // 30 or more
        } else if (a + b + c + d + e + f < 12) {
            validCombos.add(DiceCombination.TWELVE_OR_FEWER); // 12 or more
        }

        for (int count : frequencyMap.values()) {
            if (count == 2) {
                pairCount++;
            }
            if (count == 3) {
                threeOfKCount++;
            }
        }
        if (pairCount == 2)
            validCombos.add(DiceCombination.TWO_PAIRS); // two pair
        if (pairCount == 3)
            validCombos.add(DiceCombination.THREE_PAIRS); // three pair
        if (threeOfKCount == 1)
            validCombos.add(DiceCombination.THREE_OF_A_KIND); // three of a kind
        if (threeOfKCount == 2)
            validCombos.add(DiceCombination.TWO_TIMES_THREE_OF_A_KIND); // two times three of a kind

        for (int count : dice) {
            if (count % 2 == 0) {
                evenCount++;
            } else {
                oddCount++;
            }
        }
        if (evenCount == 6)
            validCombos.add(DiceCombination.ALL_EVEN); // alleven
        if (oddCount == 6)
            validCombos.add(DiceCombination.ALL_ODD); // allOdd

        if (hasLargeStraight(dice) == true)
            validCombos.add(DiceCombination.LARGE_STRAIGHT);
        if (hasSmallStraight(dice) == true)
            validCombos.add(DiceCombination.LITTLE_STRAIGHT);

        return validCombos;
    }

    private static boolean hasLargeStraight(int[] dice) {
        Arrays.sort(dice);
        // Check for any 5 consecutive numbers
        for (int i = 0; i <= dice.length - 5; i++) {
            if (isConsecutive(dice, i, 5)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasSmallStraight(int[] dice) {
        Arrays.sort(dice);
        // Check for any 4 consecutive numbers
        for (int i = 0; i <= dice.length - 4; i++) {
            if (isConsecutive(dice, i, 4)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isConsecutive(int[] dice, int start, int length) {
        for (int i = start; i < start + length - 1; i++) {
            if (dice[i] + 1 != dice[i + 1]) {
                return false; // Not consecutive
            }
        }
        return true;
    }
}
