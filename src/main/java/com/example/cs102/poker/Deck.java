package com.example.cs102.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;
    private List<Card> usedCards = new ArrayList<>();

    public Deck(List<Card> cards) {
        this.cards = cards;
        shuffleDeck();
    }

    public List<Card> getCards() {
        cards = new ArrayList<>(cards);
        return cards;
    }

    // public void setCards(List<Card> cards) {
    // this.cards = cards;
    // }

    public Card drawCard() {
        if (cards.size() > 0) {
            Card toReturn = cards.get(0);
            usedCards.add(toReturn);
            cards.remove(0);
            return toReturn;
        }
        // no cards left to draw
        return null;
    }

    public int getDeckLength() {
        return cards.size();
    }

    // if current deck is empty, fill up all the cards that has been used
    public void refreshDeck() {
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
