package com.example.cs102.poker;

import java.util.*;

public class DeckController {
    private final ArrayList<Character> suits = new ArrayList<>(Arrays.asList('d', 'c', 'h', 's'));
    private List<Card> cards;
    public DeckController(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> initCards() {
        for (Character suit : suits) {
            for (int i = 2; i <= 14; i++) {
                cards.add(new Card(suit, i));
            }
        }
        return cards;
    }
}
