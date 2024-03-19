package com.example.cs102.boss;

public class Boss{

    private int id;
    private String name;
    private double atkMultiplier;
    private int hp;
    private String difficulty;

    //create boss
    public Boss(int id, String name,double atkMultiplier,int hp, String difficulty) {
        this.id = id;
        this.name = name;
        this.atkMultiplier=atkMultiplier;
        this.hp= hp;
        this.difficulty = difficulty;
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

    public int getId(){
        return id;
    }
    public String toString(){
        String result = "";
        result += id ;
        result += ") Name: " + name;
        result +=", Difficulty = " + difficulty;
        result +=" Press "+ id;
        return result;
    }
}