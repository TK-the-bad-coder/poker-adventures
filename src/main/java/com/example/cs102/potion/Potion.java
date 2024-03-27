package com.example.cs102.potion;

public class Potion {
    private int id;
    private String itemName;
    private int gold;
    private int hp;

    public Potion(int id, String itemName, int gold, int hp) {
        this.id = id;
        this.itemName = itemName;
        this.gold = gold;
        this.hp = hp;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public int getGold() {
        return gold;
    }

    public int getHp() {
        return hp;
    }

}
