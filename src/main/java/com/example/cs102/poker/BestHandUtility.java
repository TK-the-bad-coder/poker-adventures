package com.example.cs102.poker;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BestHandUtility extends ComboUtility {

    private static List<Card> bossHand;

    public static List<Card> getBestHand(List<Card> bossCards) {
        bossHand = bossCards;
        cards = bossHand;
        cardSort();

        return Stream.<Supplier<List<Card>>>of(
            () -> getRoyalFlush(),
            () -> getStraightFlush(),
            () -> getFourOfAKind(),
            () -> getFullHouse(),
            () -> getFlush(),
            () -> getStraight(),
            () -> getThreeOfAKind(),
            () -> getTwoPair(),
            () -> getPair(),
            () -> getSingleHand())
            .map(Supplier::get)
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(Collections.emptyList());
        
        // if (getRoyalFlush() != null) {
        //     return getRoyalFlush();
        // } else if (getStraightFlush() != null) {
        //     return getStraightFlush();
        // } else if (getFourOfAKind() != null) {
        //     return getFourOfAKind();
        // } else if (getFullHouse() != null) {
        //     return getFullHouse();
        // } else if (getFlush() != null) {
        //     return getFlush();
        // } else if (getStraight() != null) {
        //     return getStraight();
        // } else if (getThreeOfAKind() != null) {
        //     return getThreeOfAKind();
        // } else if (getTwoPair() != null) {
        //     return getTwoPair();
        // }

        // else if (getPair() != null) {
        //     return getPair();
        // }
        // return getSingleHand();
    }

    private static List<Card> getRoyalFlush(){
        // out of 10 cards, if there is a royalFlush return it
        // first sort based on suit, then check if there contain a royal flush 10-j-q-k-a 
        List<Card> possibleFlush = groupedBySuit(bossHand);
        
        if(possibleFlush == null){
            return null;
        }

        // sort by value now
        List<Card> possibleRoyalFlush = possibleFlush.stream()
                .sorted(Comparator.comparing(Card::getValue))
                .collect(Collectors.toList());
        List<Card> royalFlush = new ArrayList<>();
        
        
        possibleRoyalFlush.stream()
                    .filter(card -> card.getValue() == 10)
                    .filter(card -> possibleRoyalFlush.stream().anyMatch(innerCard -> innerCard.getValue() == 11 ))
                    .filter(card -> possibleRoyalFlush.stream().anyMatch(innerCard -> innerCard.getValue() == 12 ))
                    .filter(card -> possibleRoyalFlush.stream().anyMatch(innerCard -> innerCard.getValue() == 13 ))
                    .filter(card -> possibleRoyalFlush.stream().anyMatch(innerCard -> innerCard.getValue() == 14 ))
                    .forEach(card -> royalFlush.addAll(possibleRoyalFlush.subList(possibleRoyalFlush.indexOf(card), possibleRoyalFlush.indexOf(card) + 5)));
        if(royalFlush.size() == 5){
            return royalFlush;
        }
        return null;

    }

    private static List<Card> getStraightFlush(){
        // out of 10 cards, if there is a straightFlush return it
        // first sort based on suit, followed by number, then check if there 5 card that contain a straight and flush
        List<Card> possibleFlush = groupedBySuit(bossHand);

        if(possibleFlush == null){
            return null;
        }

        // sort by value now
        List<Card> possibleStraightFlush = possibleFlush.stream()
                .sorted(Comparator.comparing(Card::getValue))
                .collect(Collectors.toList());

        List<Card> straightFlush = new ArrayList<>();
        
        
        for(int i=0; i<= possibleStraightFlush.size() - 5; i++){
            
            if(isStraight(possibleStraightFlush.subList(i, i+5))){
                List<Card> output = possibleStraightFlush.subList(i, i+5);
                for(Card card: output){
                    straightFlush.add(card);
                }
            }
        }
        
        if(straightFlush.size() == 5){
            return straightFlush;
        }
        return null;
    }

    
    
    private static List<Card> getFourOfAKind(){
        // out of 10 cards, if there is a four of a kind return it
        // first sort based on number, then check if there a same number 4 times then attached any lowest card
        
        // Filter the groups to find any group with size 4
        List<Card> fourOfAKind = groupByNumberSize(bossHand,4);
        
        boolean isfourOfAKind = false;
        if(fourOfAKind != null){
            int counter = 0;
            for(int i=0; i< bossHand.size(); i++){
                if(counter != 1){
                    if(bossHand.get(i).getValue() != fourOfAKind.get(0).getValue()){
                        fourOfAKind.add(bossHand.get(i));
                        isfourOfAKind = true;
                        counter++;
                    }
                }
               
            }
        }
        if(isfourOfAKind){
            return fourOfAKind;
        }
        return null;
    }
    
    private static List<Card> getFullHouse(){
        // out of 10 cards, if there is a FullHouse return it;
        // first sort based on number, then check if there are three number being the same, followed by another two number being same
       
        // Filter the groups to find any group with size 3
        List<Card> fullHouse = groupByNumberSize(bossHand, 3);
        
        boolean fullHouseChecker = false;
        if(fullHouse != null){
            
            for(int i=1; i< bossHand.size(); i++){
                if(bossHand.get(i-1).getValue() != fullHouse.get(0).getValue()){
                    if(bossHand.get(i-1).getValue() == bossHand.get(i).getValue()){
                        fullHouse.add(bossHand.get(i-1));
                        fullHouse.add(bossHand.get(i));
                        fullHouseChecker = true;
                    }
                }
                
            }
        }
        if(fullHouseChecker){
            return fullHouse;
        }
        return null;
    }
    
    private static List<Card> getFlush(){
        // out of 10 cards, if there is a Flush return it;
        // first sort based on suit, then number, check if there is 5 cards being the same suit, if have give the lowest 5 cards
        

        List<Card> possibleFlush = groupedBySuit(bossHand);

        if(possibleFlush == null){
            return possibleFlush;
        }
        
        while(possibleFlush.size() > 5){
            possibleFlush.remove(possibleFlush.size()-1);
        }
        return possibleFlush;
    }
    private static List<Card> getStraight(){
        // out of 10 cards, if there is a straight return it;
        // first sort based on number must be distinct, then check if there are 5 cards number in sequence,

        List<Card> straight = new ArrayList<>();
        List<Card> sortedByValue = bossHand.stream()
                .sorted(Comparator.comparing(Card::getValue))
                .collect(Collectors.toList());

        for(int i=0; i<= sortedByValue.size() - 5; i++){

            if(isStraight(sortedByValue.subList(i, i+5))){
                
                List<Card> output = sortedByValue.subList(i, i+5);

                for(Card card: output){
                    straight.add(card);
                }
                break;
            }
        }
        
        if(straight.size() == 5){
            return straight;
        }
        return null;
        
    }
    
    
    private static List<Card> getThreeOfAKind(){
        // out of 10 cards, if there is a three of a kind return it;
        // first sort based on number, then check if there are three cards being the same number, it have then match it with any two random number;
        
        // Filter the groups to find any group with size 3
        List<Card> threeOfAKind = groupByNumberSize(bossHand, 3);
        
        if(threeOfAKind != null){
            int counter =0;
            for(Card c: bossHand){
                if(counter != 2){
                    if(c.getValue() != threeOfAKind.get(0).getValue()){
                        threeOfAKind.add(c);
                        counter++;
                    }
                }
                
            }
        }

        return threeOfAKind;
        
    }

    private static List<Card> getTwoPair(){
        // out of 10 cards, if there is a pair return it;

        Map<Integer, List<Card>> groupedByValue = bossHand.stream()
                .collect(Collectors.groupingBy(Card::getValue));

        // Filter the groups to find all two pairs
        List<Card> pairs = groupedByValue.values().stream()
                                .filter(cards -> cards.size() > 1) 
                                .flatMap(List::stream) 
                                .collect(Collectors.toList());
        System.out.println(pairs.toString());
        
        List<Card> twoPairs = new ArrayList<>();
        if(pairs.size() >= 4){
            for(int i=0; i<pairs.size(); i++){
                if(i == 4){
                    break;
                }
                twoPairs.add(pairs.get(i));
            }
        }
        if(twoPairs != null){
            int counter =0;
            for(Card c: bossHand){
                if(counter != 1){
                    if(c.getValue() != twoPairs.get(0).getValue() && c.getValue() != twoPairs.get(2).getValue() ){
                        twoPairs.add(c);
                        counter++;
                    }
                }
                
            }
        }

        return null;
    }
    
    private static List<Card> getPair(){
        // out of 10 cards, if there is a pair return it;

        return groupByNumberSize(bossHand, 2);
        
    }
    private static List<Card> getSingleHand(){
        // out of 10 cards, return the last hand
        List<Card> sortedByValue = bossHand.stream()
                .sorted(Comparator.comparing(Card::getValue))
                .collect(Collectors.toList());

        List<Card> result = new ArrayList<>();
        result.add(sortedByValue.get(0));

        return result;
        
    }

    private static List<Card> groupByNumberSize(List<Card> bossHand, int number){
        Map<Integer, List<Card>> groupedByValue = bossHand.stream()
                .collect(Collectors.groupingBy(Card::getValue));

        // Filter the groups to find any group with size n
        return groupedByValue.values().stream()
                .filter(cards -> cards.size() == number)
                .findFirst().orElse(null);
    }
    private static List<Card> groupedBySuit(List<Card> bossHand){
        Map<Character, List<Card>> groupedBySuit = bossHand.stream()
                .collect(Collectors.groupingBy(Card::getSuit));

        return groupedBySuit.values().stream()
                .filter(cards -> cards.size() >= 5)
                .findFirst()
                .orElse(null);
        
    }
    
    protected static boolean isStraight(List<Card> bossHand) {
        // special case - A, 2, 3, 4, 5
        // i = 2, compare 3 - 4
        // i = 3, compare 4 - 5 = -1
        // i = 4 - this is ace, 14, ignore it
        int cardCheck = 5;
        // if the last card is 14, check only the first 4 cards
        if (bossHand.get(4).getValue() == 14 && bossHand.get(0).getValue() == 2) {
            cardCheck = 4;
        }
        for (int i = 1; i < cardCheck; i++) {
            if (bossHand.get(i - 1).getValue() - bossHand.get(i).getValue() != -1) {
                return false;
            }
        }
        
        return true;
    }

    private static int straightSum(int start, int end) {
        // AP
        return (end - start + 1) * (start + end) / 2;
    }

}
