package com.example.cs102.boss;

public class Boss{
    private String difficulty;

    private String name;
    private double atkMultiplier;
    private int hp;


    //create boss
    public Boss(String difficulty, String name,double atkMultiplier,int hp) {
        this.difficulty = difficulty;
        this.name = name;
        this.atkMultiplier=atkMultiplier;
        this.hp= hp;
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

    public double getAtkMultiplier(){
        return atkMultiplier;
    }
}