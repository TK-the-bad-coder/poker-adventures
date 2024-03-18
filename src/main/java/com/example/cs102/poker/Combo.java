// Eric
package com.example.cs102.poker;

import java.util.List;


public class Combo {
    
    private final int SINGLE_HAND = 1;
    private final int ONE_PAIR = 3;
    private final int TWO_PAIR = 10;
    private final int THREE_OF_KIND = 15;
    private final int STRAIGHT = 25;
    private final int FLUSH = 40;
    private final int FULL_HOUSE = 50;
    private final int FOUR_OF_A_KIND = 60;
    private final int STRAIGHT_FLUSH = 100;
    private final int ROYAL_FLUSH = 200;


    public int damage(List<Card> cards){
        if(is_single_hand(cards)){
            return SINGLE_HAND;
        }
        else if(is_pair(cards)){
            return ONE_PAIR;
        }
        // this have to go first
        else if(is_royal_flush(cards)){
            return ROYAL_FLUSH;
        }
        
        else if(is_four_of_kind(cards)){
            return FOUR_OF_A_KIND;
        }
        
        else if(is_full_house(cards)){
            return FULL_HOUSE;
        }

        else if(is_flush(cards)){
            return FLUSH;
        }

        else if(is_straight(cards)){
            return STRAIGHT;
        }
        
        else if(is_three_of_kind(cards)){
            return THREE_OF_KIND;
        }
        return 0;
    }

    public boolean is_single_hand(List<Card> cards){
        if(cards.size() == 1){
            return true;
        }
        return false;
    }
    public boolean is_pair(List<Card> cards){
        if(cards.size() == 2){
            if(cards.get(0).getValue() == cards.get(1).getValue()){
                return true;
            }
        }
        return false;
    }
    
    public boolean is_two_pair(List<Card> cards){
        int check = 0;
        if(cards.size() == 5){
            for(int counter = 1; counter < 5;counter++ ){
                if(cards.get(counter - 1).getValue() == cards.get(counter).getValue()){
                    check++;
                }
            }
            if(check == 2){
                return true;
            }
            
        }
        return false;
    }
    public boolean is_three_of_kind(List<Card> cards){
        int check = 0;
        if(cards.size() == 5){
            for(int counter = 1; counter < 5;counter++ ){
                if(cards.get(counter - 1).getValue() == cards.get(counter).getValue()){
                    check++;
                }
            }
            if(check == 3){
                return true;
            }
            
        }
        return false;
    }

    public boolean is_straight(List<Card> cards){
        if(cards.size() == 5){
            for(int counter = 1; counter < 5;counter++ ){
                if(cards.get(counter - 1).getValue() != cards.get(counter).getValue() - 1){
                    return false;
                }
            }
            return true;
            
        }
        return false;
    }

    public boolean is_flush(List<Card> cards){

        if(cards.size() == 5){
            for(int counter = 1; counter < 5;counter++ ){
                if(cards.get(counter - 1).getSuit() != cards.get(counter).getSuit()){
                    return false;
                }
            }
            return true;
            
        }
        return false;
    }

    public boolean is_full_house(List<Card> cards){
        int check1 = 0;
        int check2 = 0;
        if(cards.size() == 5){
            // check first three cards 
            for(int counter = 1; counter < 3;counter++ ){
                if(cards.get(counter - 1).getValue() == cards.get(counter).getValue()){
                    check1++;
                }
            }

            if(check1 == 3){
                if(cards.get(4).getValue() == cards.get(5).getValue){
                    return true;
                }
            }
            
        }
        return false;
    }

    public boolean is_four_of_kind(List<Card> cards){
        int check = 0;
        if(cards.size() == 5){
            for(int counter = 1; counter < 5;counter++ ){
                if(cards.get(counter - 1).getValue() == cards.get(counter).getValue()){
                    check++;
                }
            }
            if(check == 4){
                return true;
            }
            
        }
        return false;
    }
    public boolean is_straight_flush(List<Card> cards){
        int check = 0;
        if(cards.size() == 5){
            // differentiate between straight flush vs Royal flush
            if(cards.get(0).value() != 10 || cards.get(4).value() != 14 ){
                if(is_flush(cards) && is_straight(cards)){
                    return true;
                }
            }
        }
        
        return false;
    }
    public boolean is_royal_flush(List<Card> cards){
        int check = 0;
        if(cards.size() == 5){
            // differentiate between straight flush vs Royal flush
            if(cards.get(0).value() == 10 && cards.get(4).value() == 14 ){
                if(is_flush(cards) && is_straight(cards)){
                    return true;
                }
            }
        }
        return false;
    }

}
