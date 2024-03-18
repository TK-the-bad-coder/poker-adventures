package com.example.cs102.Comparators;
import java.util.*;

import com.example.cs102.poker.Card;
public class SuitComparator implements Comparator <Card>{
    public int compare (Card card1 , Card card2){
        if (card1.getSuit() != card2.getSuit()){
            return (int)(card1.getSuit() - card2.getSuit());
        }
        return card1.getValue() - card2.getValue();
    }
}

