package com.example.cs102.player;

import java.util.List;

import com.example.cs102.hand.PlayerHand;
import com.example.cs102.poker.Card;

public class Player {
    
    private PlayerHand playerHand;

    private int id;

    private String name;

    private int hp = 50; // default value

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // overload for existing players
    public Player(int id, String name, int hp) {
        this.id = id;
        this.name = name;
        this.hp = hp;
    }

    public int getId(){
        return id;
    }
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public void setHand(PlayerHand playerHand) {
        this.playerHand = playerHand;
    }

    public void clearHand() {
        this.playerHand = null;
    }

    public List<Card> getCards() {
        return this.playerHand.getHand();
    }

    public PlayerHand getHand() {
        return this.playerHand;
    }
    
    public boolean isDead(){

        return hp < 0;

        // if(hp < 0){
        //     return true;
        // }
        // return false;
    }

    // private final static MAX_CARDS = 10;
}
