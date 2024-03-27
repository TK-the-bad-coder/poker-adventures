package com.example.cs102.Comparators;
import java.util.Comparator;

import com.example.cs102.poker.Card;
public class ValueComparator implements Comparator <Card>{
    public int compare(Card card1, Card card2){
        if (card1.getValue() != card2.getValue()){
            return card1.getValue() - card2.getValue();
        }
        return (int)(card1.getSuit() - card2.getSuit());
    }
}

