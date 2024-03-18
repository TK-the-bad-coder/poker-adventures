package com.example.cs102.hand;

import com.example.cs102.poker.Card;
import com.example.cs102.poker.Deck;
import com.example.cs102.Comparators.*;

public class PlayerHand extends Hand {

    public PlayerHand(Deck deck) {
        super(deck);
        currentHand.sort(new ValueComparator());
        showHand();
    }

    // show hand
    private void showHand() {
        for (Card card : currentHand) {
            System.out.print("|");
            System.out.print(card.getSpecialOutput());
        }
        System.out.print("|");
        System.out.println("");
        System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |");

    }

}
