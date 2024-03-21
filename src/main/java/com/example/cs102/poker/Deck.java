package com.example.cs102.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;
    private List<Card> usedCards = new ArrayList<>();
    private int refreshCount = 0;

    public Deck(List<Card> cards) {
        this.cards = cards;
        shuffleDeck();
    }

    public List<Card> getCards() {
        // cards = new ArrayList<>(cards);
        return cards == null ? new ArrayList<Card>() : cards;
    }

    public Card drawCard() {
        // players can refresh their deck up to twice

        Card toReturn = null;

        // System.out.println(cards.toString());
        // cards.remove(0);
        if (getDeckLength() == 0 && refreshCount != 2) {
            refreshDeck();
            refreshCount++;
        }
        toReturn = cards.remove(0);

        return toReturn;

    }

    public void addToDiscardPile(Card card) {
        usedCards.add(card);
    }

    public int getDeckLength() {
        return cards.size();
    }

    // if current deck is empty, fill up all the cards that has been used
    private void refreshDeck() {
        System.out.println("===========DECK REFRESHED==========" + usedCards.size());
        for (Card card : usedCards) {
            cards.add(card);
        }
        shuffleDeck();
        usedCards.clear();

    }

    public int getUsedLength() {
        return usedCards.size();
    }

    private void shuffleDeck() {
        Collections.shuffle(cards);
    }

}
