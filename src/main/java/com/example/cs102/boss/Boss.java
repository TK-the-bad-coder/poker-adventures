package com.example.cs102.boss;

import java.util.List;

import com.example.cs102.hand.BossHand;
import com.example.cs102.hand.Hand;
import com.example.cs102.poker.Card;

public class Boss {

    private Hand bossHand;
    private int id;
    private String name;
    private double atkMultiplier;
    private int hp;
    private String difficulty;
    private int gold;

    // create boss
    public Boss(int id, String name, double atkMultiplier, int hp, String difficulty, int gold) {
        this.id = id;
        this.name = name;
        this.atkMultiplier = atkMultiplier;
        this.hp = hp;
        this.difficulty = difficulty;
        this.gold = gold;
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

    public String getDifficulty() {
        return difficulty;
    }

    public double getAtkMultiplier() {
        return atkMultiplier;
    }

    public int getId() {
        return id;
    }

    public int getGold(){
        return gold;
    }

    public void setHand(BossHand bossHand) {
        this.bossHand = bossHand;
    }

    public Hand getHand() {
        return bossHand;
    }

    public List<Card> getCards() {
        return this.bossHand.getHand();
    }

    public String toString() {
        String result = "";
        result += id;
        result += ") Name: " + name;
        result += ", Difficulty = " + difficulty;
        result += ", Reward =" + gold;
        result += " Press " + id;
        
        return result;
    }
}