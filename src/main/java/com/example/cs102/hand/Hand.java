package com.example.cs102.hand;

import com.example.cs102.poker.Card;
import com.example.cs102.poker.Deck;
import java.util.*;

public class Hand {
    private List<Card> currentHand;
    private final int maxHand = 10;
    // create Hand

    public Hand(Deck deck) {

        // add cards to hand array
        this.currentHand = new ArrayList<>();
        String toReturn = "";
        for (int i = 0; i < 10; i++) {
            Card card = deck.drawCard();
            this.currentHand.add(card);
            toReturn += card.getValue() + " of " + card.getSuit() + ", ";
        }

        String toReturnEdited = toReturn.substring(0, toReturn.length() - 2);
        System.out.println(toReturnEdited);
    }

    // add to hand if less than 10, return false if cannot, true if successful
    public boolean addToHand(Deck deck) {
        
        //if hand<10, add card
        while (currentHand.size() < maxHand) {
            if(deck.getDeckLength()<10){
                break;
            }
            
            Card card = deck.drawCard();
            if (card==null){
                return false;
            }
            currentHand.add(card);
        }
        return true;
    }

    // figure out a way to remove card from hand
    public void discard(String input) {

        // split input
        String[] substrings = input.split("");

        // get the cards to remove based on substring, assuming

        List<Card> temp = new ArrayList<>();

        // if set value of element to null based on index
        for (String x : substrings) {
            for (int num = 0; num < 10; num++) {
                if (num == Integer.parseInt(x)) {
                    currentHand.set(num, null);
                }
            }
        }
        currentHand.removeIf(element -> element == null);
        this.showHand();

    }

    // show hand
    public void showHand() {

        String toReturn = "";
        for (Card card : currentHand) {

            toReturn += card.getValue() + " of " + card.getSuit() + ", ";
        }

        String toReturnEdited = toReturn.substring(0, toReturn.length() - 2);
        
        System.out.println(toReturnEdited);
    }
}
