package com.example.cs102.poker;

// by nicholas

public class Card implements Comparable<Card>{

    private static final String ANSI_HEART = "\u001B[47m\u001B[31m\u2764\u001B[0m";
    private static final String ANSI_DIAMOND = "\u001B[47m\u001B[31m\u2666\u001B[0m";
    private static final String ANSI_CLUB = "\u001B[47m\u001B[30m\u2663\u001B[0m";
    private static final String ANSI_SPADE = "\u001B[47m\u001B[30m\u2660\u001B[0m";

    private char suit;
    private int value;

    public Card(char suit, int value) {
        this.suit = suit;
        this.value = value;
    };

    public char getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String getSpecialOutput() {
        String result = "";
        String suit = "";
        String value = "";

        // System.out.print("|");
        if (getValue() != 10) {
            result += " ";
        }
        switch (getValue()) {
            case 11:
                value = "J";
                break;
            case 12:
                value = "Q";
                break;
            case 13:
                value = "K";
                break;
            case 14:
                value = "A";
                break;
            default:
                value = Integer.toString(getValue());
        }

        switch (getSuit()) {
            case 's':
                suit = ANSI_SPADE;
                break;
            case 'c':
                suit = ANSI_CLUB;
                break;
            case 'h':
                suit = ANSI_HEART;
                break;
            case 'd':
                suit = ANSI_DIAMOND;
                break;
        }
        result += value + suit;
        return result;

    }
    @Override
    public int compareTo(Card anotherCard) {
        int cmp = getValue() - anotherCard.getValue();
        return cmp;
    }
}
