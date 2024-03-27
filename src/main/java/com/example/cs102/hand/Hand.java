package com.example.cs102.hand;

import com.example.cs102.poker.Card;
import com.example.cs102.poker.Deck;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    protected List<Card> currentHand;
    private static final int MAXHAND = 10;
    protected Deck deck;

    // create Hand
    public Hand(Deck deck) {
        this.deck = deck;
        this.currentHand = new ArrayList<>();
        addToHand();
    }

    public List<Card> getCards() {
        return currentHand;
    }

    // add to hand
    public void addToHand() {
        while (currentHand.size() < MAXHAND) {
            currentHand.add(deck.drawCard());
        }
    }

    public void discard(List<Card> discards) {
        for (Card discard : discards) {
            currentHand.removeIf(card -> card.getValue() == discard.getValue() && card.getSuit() == discard.getSuit());
            deck.addToDiscardPile(discard);
        }
        showDiscards(discards);
    }

    private void showDiscards(List<Card> cards) {
        for (Card card : cards) {
            System.out.print("|");
            System.out.print(card.getSpecialOutput());
        }
        System.out.print("|\n");
    }

}
