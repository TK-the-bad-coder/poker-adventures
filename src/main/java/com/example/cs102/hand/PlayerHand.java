package com.example.cs102.hand;

import com.example.cs102.poker.Deck;
import com.example.cs102.Comparators.*;

public class PlayerHand extends Hand{
    
    public PlayerHand(Deck deck) {
        super(deck);
        currentHand.sort(new ValueComparator());
        showHand();
    }
}
