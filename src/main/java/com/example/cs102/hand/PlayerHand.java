package com.example.cs102.hand;

import com.example.cs102.poker.Deck;

public class PlayerHand extends Hand{
    
    public PlayerHand(Deck deck) {
        super(deck);
        showHand();
    }
}
