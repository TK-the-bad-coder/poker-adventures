package com.example.cs102.poker;

import java.util.*;

public class DeckController {
    private final HashMap<Character, String> suits = new HashMap<Character, String>() {{
        put('h', "Hearts");
        put('d', "Diamonds");
        put('s', "Spades");
        put('c', "Clubs");

    }};
    private List<Card> cards;
    public DeckController(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> initCards() {
        suits.keySet().forEach(suit -> {
            for (int i = 2; i <= 14; i++) {
                cards.add(new Card(suit, i));
            }
        });
        // for (Character entry : suits.entrySet()) {
        //     for (int i = 2; i <= 14; i++) {
        //         cards.add(new Card(suit, i));
        //     }
        // }
        return cards;
    }
}
