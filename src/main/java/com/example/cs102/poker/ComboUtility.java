
package com.example.cs102.poker;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ComboUtility {

    // Declare the static final map
    protected static final Map<String, Integer> COMBO_MAP;

    // Initialize the static final map in a static block
    static {
        COMBO_MAP = new HashMap<>();
        COMBO_MAP.put("HIGH CARD", 1);
        COMBO_MAP.put("ONE PAIR", 3);
        COMBO_MAP.put("TWO PAIR", 10);
        COMBO_MAP.put("THREE OF A KIND", 15);
        COMBO_MAP.put("STRAIGHT", 25);
        COMBO_MAP.put("FLUSH", 40);
        COMBO_MAP.put("FULL HOUSE", 60);
        COMBO_MAP.put("FOUR OF A KIND", 90);
        COMBO_MAP.put("STRAIGHT FLUSH", 150);
        COMBO_MAP.put("ROYAL FLUSH", 300);
    }

    protected static List<Card> cards;

    public static String getHandValue(List<Card> selectedCards) {
        cards = selectedCards;
        // sort the card by VALUE (ignore suit)
        cardSort();
        int cardSize = cards.size();
        // break it further
        if (cardSize < 5) {
            if (isSingleHand()) {
                return "HIGH CARD";
                // return SINGLE_HAND;
            } else if (cardSize == 2 && isPair(cards.get(0), cards.get(1))) {
                return "ONE PAIR";
            }

        } else if (cardSize == 5) {
            if (isRoyalFlush()) {
                return "ROYAL FLUSH";
            } else if (isStraightFlush()) {
                return "STRAIGHT FLUSH";
            } else if (isFourOfKind()) {
                return "FOUR OF A KIND";
            } else if (isFullHouse()) {
                return "FULL HOUSE";
            } else if (isFlush()) {
                return "FLUSH";
            } else if (isStraight()) {
                return "STRAIGHT";
            } else if (isThreeOfKind()) {
                return "THREE OF A KIND";
            } else if (isTwoPair()) {
                return "TWO PAIR";
            }
            // user botched cases
            else if (isOnePair()) {
                // special case - user decides to enter 5 cards to trigger 1 pair
                // can also trigger error prevention by suggesting user to only throw
                // one pair
                return "ONE PAIR";
            } else {
                // however, we can be nice and do a hand check for
                // them for "error prevention"
                return "HIGH CARD"; // no feasible hand, returns single damage value
            }

            // to include a condition where a user selects 5 cards but contains a pair

        }
        return "HIGH CARD"; // default worst hand

    }

    public static int getDamageValue(String comboPlayed) {
        return COMBO_MAP.get(comboPlayed);
    }

    protected static boolean isSingleHand() {
        return cards.size() == 1;
    }

    protected static boolean isPair(Card card1, Card card2) {
        return card1.getValue() == card2.getValue();
    }

    protected static boolean isOnePair() {
        // only used when someone keys in 5 cards
        for (int i = 1; i < 5; i++) {
            if (isPair(cards.get(i - 1), cards.get(i))) {
                return true;
            }
        }
        return false;
    }

    protected static boolean isTwoPair() {
        for (int i = 0; i < 4; i += 2) {
            if (!isPair(cards.get(i), cards.get(i + 1))) {
                return false;
            }
        }
        return true;
    }

    protected static boolean isThreeOfKind() {
        return cards.stream().filter(i -> i.getValue() == cards.get(0).getValue()).count() == 3;
    }

    protected static boolean isStraight() {
        // special case - A, 2, 3, 4, 5
        // i = 2, compare 3 - 4
        // i = 3, compare 4 - 5 = -1
        // i = 4 - this is ace, 14, ignore it
        int cardCheck = 5;
        if (cards.get(4).getValue() == 14 && cards.get(0).getValue() == 2) {
            cardCheck = 4;
        }
        for (int i = 1; i < cardCheck; i++) {
            if (cards.get(i - 1).getValue() - cards.get(i).getValue() != -1) {
                return false;
            }
        }
        return true;
    }

    protected static boolean isFlush() {
        for (int counter = 1; counter < 5; counter++) {
            if (cards.get(counter - 1).getSuit() != cards.get(counter).getSuit()) {
                return false;
            }
        }
        return true;
    }

    protected static boolean isFullHouse() {
        return isThreeOfKind() && isPair(cards.get(3), cards.get(4));
    }

    protected static boolean isFourOfKind() {
        return cards.stream().filter(i -> i.getValue() == cards.get(0).getValue()).count() == 4;
    }

    protected static boolean isStraightFlush() {
        // differentiate between straight flush vs Royal flush
        if (cards.get(0).getValue() != 10 && cards.get(4).getValue() != 14) {
            return isFlush() && isStraight();
        }
        return false;
    }

    protected static boolean isRoyalFlush() {
        // differentiate between straight flush vs Royal flush
        if (cards.get(0).getValue() == 10 && cards.get(4).getValue() == 14) {
            return isFlush() && isStraight();
        }
        return false;
    }

    /**
     * Helper function to sort the selected cards by value with special conditions
     * example:
     * selected cards 1: [2, 4, 2, 4, 2] -> [2, 2, 2, 4, 4]
     * selected cards 2: [4, 9, 4, 4, 9] -> [4, 4, 4, 9, 9]
     * selected cards 3: [1, 9, 7, 9, 7] -> [7, 7, 9, 9, 1]
     * selected cards 4: [4, 9, 1, 2, 5] -> [1, 2, 4, 5, 9]
     */
    protected static void cardSort() {

        // create a hashmap that contains
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        // put card into maps based by value
        for (Card card : cards) {
            frequencyMap.put(card.getValue(), frequencyMap.getOrDefault(card.getValue(), 0) + 1);
        }

        // sort cards based on a certain criterias
        Collections.sort(cards, (card1, card2) -> {
            // compare the number of frequency of the value in the map
            int freqCompare = frequencyMap.get(card2.getValue()).compareTo(frequencyMap.get(card1.getValue()));

            // if frequency is exactly the same, or all cards are unique
            // sort in ascending order
            if (freqCompare == 0) {
                return card1.compareTo(card2);
            }
            // sort by frequency
            return freqCompare;
        });
    }

}
