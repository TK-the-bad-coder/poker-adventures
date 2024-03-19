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
        cards = new ArrayList<>(cards);
        return cards;
    }

    public Card drawCard() {
        // players can refresh their deck up to twice
        if (getDeckLength() == 0 && refreshCount != 2) {
            refreshDeck();
            refreshCount++;
        }
        Card toReturn = cards.get(0);
        usedCards.add(toReturn);
        cards.remove(0);
        return toReturn;

        // no cards left to draw
        // return null;
    }

    public int getDeckLength() {
        return cards.size();
    }

    // if current deck is empty, fill up all the cards that has been used
    private void refreshDeck() {
        for (Card card : usedCards) {
            this.cards.add(card);
        }
        shuffleDeck();
        usedCards.clear();
    }

    private void shuffleDeck() {
        Collections.shuffle(cards);
    }

}
