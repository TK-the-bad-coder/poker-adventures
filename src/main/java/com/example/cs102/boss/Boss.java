package com.example.cs102.boss;

public class Boss{
    private int id;

    private String name;
    private double atkMultiplier;
    private int hp;


    //create boss
    public Boss(int id, String name,double atkMultiplier,int hp) {
        this.id = id;
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
}