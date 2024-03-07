package com.example.cs102.hand;
import com.example.cs102.poker.Card;
import com.example.cs102.poker.Deck;
import java.util.*;
public class Hand {
    private List<Card> currentHand;
    private final int maxHand=10;
    //create Hand
    public Hand(Deck deck){
        this.currentHand=new ArrayList<>();

        for(int i=0;i<10;i++){
            Card card = deck.drawCard();
            this.currentHand.add(card);
            System.out.print(card.getValue() + " of " + card.getSuit()+", ");
        }
    }
    //add to hand
    public void addToHand(Card card){
        if (currentHand.size()<maxHand){
            currentHand.add(card);
        }
    }
//figure out a way to remove card from hand
    
}
