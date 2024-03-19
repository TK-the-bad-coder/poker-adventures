package com.example.cs102.hand;

import com.example.cs102.poker.Card;
import com.example.cs102.poker.Deck;
import java.util.*;
import java.util.stream.Collectors;

public class Hand {
    protected List<Card> currentHand;
    private final int MAXHAND = 10;
    protected Deck deck;
    // private static final String ANSI_RESET = "\u001B[0m";
    // private static final String ANSI_BLACK = "\u001B[30m";
    // private static final String ANSI_RED = "\u001B[31m";

    // create Hand
    public Hand(Deck deck) {
        this.deck = deck;
        this.currentHand = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Card card = this.deck.drawCard();
            this.currentHand.add(card);
        }
        // showHand();
    }

    public List<Card> getHand() {
        return currentHand;
    }

    // add to hand
    public void addToHand() {
        while (currentHand.size() < MAXHAND) {
            currentHand.add(deck.drawCard());
        }
    }
    // figure out a way to remove card from hand

    public void discard(List<Card> cards) {
        currentHand = currentHand.stream().filter(card -> !cards.contains(card)).collect(Collectors.toList());
        showDiscards(cards);
    }

    private void showDiscards(List<Card> cards) {
        for (Card card : cards) {
            System.out.print("|");
            System.out.print(card.getSpecialOutput());
        }
        System.out.print("|\n");
    }

}
