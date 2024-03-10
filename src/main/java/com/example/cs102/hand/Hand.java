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
    public void discard(String input){
        
        //split input
        String[] substrings =input.split("");
        
        //get the cards to remove based on substring, assuming 

        //add to temp storage
        List<Card> temp= new ArrayList<>();
        for(Card card:currentHand){
            for()
            if
            temp.add(card);
        }

        //remove based on old index list
        

    }

//show hand
    public void showHand(){
        
        
        for(Card card:currentHand){
            
            System.out.print(card.getValue() + " of " + card.getSuit()+", ");
        }
    }
}
