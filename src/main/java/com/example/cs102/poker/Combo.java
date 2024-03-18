// Eric
package com.example.cs102.poker;

import java.util.List;

public class Combo {

    private static final int SINGLE_HAND = 1;
    private static final int ONE_PAIR = 3;
    private static final int TWO_PAIR = 10;
    private static final int THREE_OF_KIND = 15;
    private static final int STRAIGHT = 25;
    private static final int FLUSH = 40;
    private static final int FULL_HOUSE = 50;
    private static final int FOUR_OF_A_KIND = 60;
    private static final int STRAIGHT_FLUSH = 100;
    private static final int ROYAL_FLUSH = 200;

    private static List<Card> cards;
    public static int damage(List<Card> selectedCards) {
        cards = selectedCards;
        if (isSingleHand()) {
            return SINGLE_HAND;
        } else if (isPair()) {
            return ONE_PAIR;
        } else if (isTwoPair()) {
            return TWO_PAIR;
        }
        // this have to go first
        else if (isRoyalFlush()) {
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
            return THREE_OF_KIND;
        }
        return 0;
    }

    private static boolean isSingleHand() {
        if (cards.size() == 1) {
            return true;
        }
        return false;
    }

    private static boolean isPair() {
        if (cards.size() == 2) {
            if (cards.get(0).getValue() == cards.get(1).getValue()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTwoPair() {
        int check = 0;
        if (cards.size() == 5) {
            for (int counter = 1; counter < 5; counter++) {
                if (cards.get(counter - 1).getValue() == cards.get(counter).getValue()) {
                    check++;
                }
            }
            if (check == 2) {
                return true;
            }

        }
        return false;
    }

    private static boolean isThreeOfKind() {
        int check = 0;
        if (cards.size() == 5) {
            for (int counter = 1; counter < 5; counter++) {
                if (cards.get(counter - 1).getValue() == cards.get(counter).getValue()) {
                    check++;
                }
            }
            if (check == 3) {
                return true;
            }

        }
        return false;
    }

    private static boolean isStraight() {
        if (cards.size() == 5) {
            for (int counter = 1; counter < 5; counter++) {
                if (cards.get(counter - 1).getValue() != cards.get(counter).getValue() - 1) {
                    return false;
                }
            }
            return true;

        }
        return false;
    }

    private static boolean isFlush() {

        if (cards.size() == 5) {
            for (int counter = 1; counter < 5; counter++) {
                if (cards.get(counter - 1).getSuit() != cards.get(counter).getSuit()) {
                    return false;
                }
            }
            return true;

        }
        return false;
    }

    private static boolean isFullHouse() {
        int check1 = 0;
        int check2 = 0;
        if (cards.size() == 5) {
            // check first three cards
            for (int counter = 1; counter < 3; counter++) {
                if (cards.get(counter - 1).getValue() == cards.get(counter).getValue()) {
                    check1++;
                }
            }

            if (check1 == 3) {
                if (cards.get(4).getValue() == cards.get(5).getValue()) {
                    return true;
                }
            }

        }
        return false;
    }

    private static boolean isFourOfKind() {
        int check = 0;
        if (cards.size() == 5) {
            for (int counter = 1; counter < 5; counter++) {
                if (cards.get(counter - 1).getValue() == cards.get(counter).getValue()) {
                    check++;
                }
            }
            if (check == 4) {
                return true;
            }

        }
        return false;
    }

    private static boolean isStraightFlush() {
        int check = 0;
        if (cards.size() == 5) {
            // differentiate between straight flush vs Royal flush
            if (cards.get(0).getValue() != 10 || cards.get(4).getValue() != 14) {
                if (isFlush() && isStraight()) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isRoyalFlush() {
        int check = 0;
        if (cards.size() == 5) {
            // differentiate between straight flush vs Royal flush
            if (cards.get(0).getValue() == 10 && cards.get(4).getValue() == 14) {
                if (isFlush() && isStraight()) {
                    return true;
                }
            }
        }
        return false;
    }

}
