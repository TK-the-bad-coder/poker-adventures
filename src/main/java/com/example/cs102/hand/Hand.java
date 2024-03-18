package com.example.cs102.hand;

import com.example.cs102.poker.Card;
import com.example.cs102.poker.Deck;
import java.util.*;

public class Hand {
    protected List<Card> currentHand;
    private final int maxHand = 10;
    // private static final String ANSI_RESET = "\u001B[0m";
    // private static final String ANSI_BLACK = "\u001B[30m";
    // private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_HEART = "\u001B[47m\u001B[31m\u2764\u001B[0m";
    private static final String ANSI_DIAMOND = "\u001B[47m\u001B[31m\u2666\u001B[0m";
    private static final String ANSI_CLUB = "\u001B[47m\u001B[30m\u2663\u001B[0m";
    private static final String ANSI_SPADE = "\u001B[47m\u001B[30m\u2660\u001B[0m";

    // create Hand
    public Hand(Deck deck) {
        this.currentHand = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Card card = deck.drawCard();
            this.currentHand.add(card);
        }
        // showHand();
    }

    // add to hand
    public void addToHand(Card card) {
        if (currentHand.size() < maxHand) {
            currentHand.add(card);
        }
    }
    // figure out a way to remove card from hand

    public void discard(String input) {

        // split input
        String[] substrings = input.split("");

        // get the cards to remove based on substring, assuming

        // add to temp storage
        List<Card> temp = new ArrayList<>();
        // for(Card card:currentHand){
        // for()
        // if
        // temp.add(card);
        // }

        // remove based on old index list

    }

    // show hand
    public void showHand() {
        String suit = "";
        String value = "";
        for (Card card : currentHand) {

            switch (card.getSuit()) {
                case 's':
                    suit = ANSI_SPADE;
                    break;
                case 'c':
                    suit = ANSI_CLUB;
                    break;
                case 'h':
                    suit = ANSI_HEART;
                    break;
                case 'd':
                    suit = ANSI_DIAMOND;
                    break;
            }
            System.out.print("|");
            if (card.getValue() != 10) {
                System.out.print(" ");
            }
            switch (card.getValue()) {
                case 11:
                    value = "J";
                    break;
                case 12:
                    value = "Q";
                    break;
                case 13:
                    value = "K";
                    break;
                case 14:
                    value = "A";
                    break;
                default:
                    value = Integer.toString(card.getValue());
            }
            System.out.print(value + suit);

        }
        System.out.print("|");
        System.out.println("");
        System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |");

    }

}
