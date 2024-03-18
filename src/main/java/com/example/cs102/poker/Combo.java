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

    private List<Card> card;
    public static int damage(List<Card> cards) {
        if (isSingleHand(cards)) {
            return SINGLE_HAND;
        } else if (isPair(cards)) {
            return ONE_PAIR;
        } else if (isTwoPair(cards)) {
            return TWO_PAIR;
        }
        // this have to go first
        else if (isRoyalFlush(cards)) {
            return ROYAL_FLUSH;
        }

        else if (isStraightFlush(cards)) {
            return STRAIGHT_FLUSH;
        }

        else if (isFourOfKind(cards)) {
            return FOUR_OF_A_KIND;
        }

        else if (isFullHouse(cards)) {
            return FULL_HOUSE;
        }

        else if (isFlush(cards)) {
            return FLUSH;
        }

        else if (isStraight(cards)) {
            return STRAIGHT;
        }

        else if (isThreeOfKind(cards)) {
            return THREE_OF_KIND;
        }
        return 0;
    }

    private static boolean isSingleHand(List<Card> cards) {
        if (cards.size() == 1) {
            return true;
        }
        return false;
    }

    private static boolean isPair(List<Card> cards) {
        if (cards.size() == 2) {
            if (cards.get(0).getValue() == cards.get(1).getValue()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTwoPair(List<Card> cards) {
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

    private static boolean isThreeOfKind(List<Card> cards) {
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

    private static boolean isStraight(List<Card> cards) {
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

    private static boolean isFlush(List<Card> cards) {

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

    private static boolean isFullHouse(List<Card> cards) {
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

    private static boolean isFourOfKind(List<Card> cards) {
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

    private static boolean isStraightFlush(List<Card> cards) {
        int check = 0;
        if (cards.size() == 5) {
            // differentiate between straight flush vs Royal flush
            if (cards.get(0).getValue() != 10 || cards.get(4).getValue() != 14) {
                if (isFlush(cards) && isStraight(cards)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isRoyalFlush(List<Card> cards) {
        int check = 0;
        if (cards.size() == 5) {
            // differentiate between straight flush vs Royal flush
            if (cards.get(0).getValue() == 10 && cards.get(4).getValue() == 14) {
                if (isFlush(cards) && isStraight(cards)) {
                    return true;
                }
            }
        }
        return false;
    }

}
