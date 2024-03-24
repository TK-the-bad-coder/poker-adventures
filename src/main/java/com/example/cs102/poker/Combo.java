// Eric 
// Nathan passed away while refactoring this
package com.example.cs102.poker;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import com.example.cs102.Exceptions.InvalidHandException;
import com.example.cs102.Exceptions.DuplicateCardException;

public class Combo {

    // Declare the static final map
    private static final Map<String, Integer> COMBO_MAP;

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

    private static final int SINGLE_HAND = 1;
    private static final int ONE_PAIR = 3;
    private static final int TWO_PAIR = 10;
    private static final int THREE_OF_A_KIND = 15;
    private static final int STRAIGHT = 25;
    private static final int FLUSH = 40;
    private static final int FULL_HOUSE = 50;
    private static final int FOUR_OF_A_KIND = 60;
    private static final int STRAIGHT_FLUSH = 100;
    private static final int ROYAL_FLUSH = 200;

    private static List<Card> cards;

    public static int damage(List<Card> selectedCards) {
        cards = selectedCards;
        // sort the card by VALUE (ignore suit)
        cardSort();
        int cardSize = cards.size();
        // break it further
        if (cardSize < 5) {
            if (isSingleHand()) {
                return SINGLE_HAND;
            } else if (cardSize == 2 && isPair(cards.get(0), cards.get(1))) {
                return ONE_PAIR;
            }

        } else if (cardSize == 5) {

            // this have to go first
            if (isRoyalFlush()) {
                return ROYAL_FLUSH;
            }

            else if (isStraightFlush()) {
                return STRAIGHT_FLUSH;
            }

            else if (isFourOfKind()) {
                return FOUR_OF_A_KIND;
            }

            else if (isFullHouse()) {
                return FULL_HOUSE;
            }

            else if (isFlush()) {
                return FLUSH;
            }

            else if (isStraight()) {
                return STRAIGHT;
            }

            else if (isThreeOfKind()) {
                return THREE_OF_A_KIND;
            }
            if (isTwoPair()) {
                return TWO_PAIR;
            }
            // user botched cases
            else if (isOnePair()) {
                // special case - user decides to enter 5 cards to trigger 1 pair
                // can also trigger error prevention by suggesting user to only throw
                // one pair
                return ONE_PAIR;
            } else {
                // however, we can be nice and do a hand check for
                // them for "error prevention"
                return SINGLE_HAND; // no feasible hand, returns single damage value
            }

            // to include a condition where a user selects 5 cards but contains a pair

        }
        return 0; // user did not select either 1, 2 or 5 cards
    }

    private static boolean isSingleHand() {
        return cards.size() == 1;
    }

    private static boolean isPair(Card card1, Card card2) {
        return card1.getValue() == card2.getValue();

    }

    private static boolean isOnePair() {
        // only used when someone keys in 5 cards
        for (int i = 1; i < 5; i++) {
            if (isPair(cards.get(i - 1), cards.get(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTwoPair() {
        for (int i = 0; i < 4; i += 2) {
            if (!isPair(cards.get(i), cards.get(i + 1))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isThreeOfKind() {
        // int check = 0;
        // for (int counter = 1; counter < 5; counter++) {
        // if (cards.get(counter - 1).getValue() == cards.get(counter).getValue()) {
        // check++;
        // }
        // }
        // return check == 3;
        return cards.stream().filter(i -> i.getValue() == cards.get(0).getValue()).count() == 3;
    }

    private static boolean isStraight() {
        // special case - A, 2, 3, 4, 5
        // i = 2, compare 3 - 4
        // i = 3, compare 4 - 5 = -1
        // i = 4 - this is ace, 14, ignore it
        int cardCheck = 5;
        // if the last card is 14, check only the first 4 cards
        if (cards.get(4).getValue() == 14 && cards.get(0).getValue() == 2) {
            cardCheck = 4;
        }
        for (int i = 1; i < cardCheck; i++) {
            if (cards.get(i - 1).getValue() - cards.get(i).getValue() != -1) {
                return false;
            }
        }

        // for (int counter = 1; counter < 5; counter++) {

        // if (cards.get(counter - 1).getValue() != cards.get(counter).getValue() - 1) {
        // return false;
        // }
        // }
        return true;

        // return false;
    }

    private static boolean isFlush() {
        for (int counter = 1; counter < 5; counter++) {
            if (cards.get(counter - 1).getSuit() != cards.get(counter).getSuit()) {
                return false;
            }
        }
        return true;
    }

    private static boolean isFullHouse() {
        // int check = 0;
        // check first three cards

        // for (int counter = 1; counter < 3; counter++) {
        // if (cards.get(counter - 1).getValue() == cards.get(counter).getValue()) {
        // check++;
        // }
        // }

        return isThreeOfKind() && isPair(cards.get(3), cards.get(4));
        // cards.get(3).getValue() == cards.get(4).getValue();

    }

    private static boolean isFourOfKind() {
        return cards.stream().filter(i -> i.getValue() == cards.get(0).getValue()).count() == 4;
    }

    private static boolean isStraightFlush() {
        // differentiate between straight flush vs Royal flush
        if (cards.get(0).getValue() != 10 && cards.get(4).getValue() != 14) {
            return isFlush() && isStraight();
        }
        return false;
        // if (isFlush() && isStraight()) {
        // return true;
        // }
        // }

        // return false;
    }

    private static boolean isRoyalFlush() {
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
    private static void cardSort() {

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

    public static void handChecker(int[] input) {
        int handSize = input.length;

        IntStream integerStream = Arrays.stream(input);
        // error checking

        if (handSize != 1 || handSize != 2 || handSize > 5) {
            throw new InvalidHandException("Please enter a valid hand length!");
        } else if (integerStream.distinct().toArray().length != handSize) {
            throw new DuplicateCardException("Hand contains duplicate choices! Please ensure all numbers are unique.");
        } else if (integerStream.filter(num -> num < 0 || num > 9).toArray().length > 0) {
            throw new IllegalArgumentException(
                    "Your input contains an invalid number! Please key in numbers only from 0 to 9");
        }
        // if (handSize != 1 && handSize != 2 && handSize != 5 ){
        // throw new InvalidHandException();
        // }
        // // checking for duplicates
        // Set <Card> handSet= new HashSet<Card>(hand);
        // if (handSet.size() != hand.size()){
        // throw new DuplicateCardException();
        // }
    }
}
