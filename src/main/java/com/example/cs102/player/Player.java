package com.example.cs102.player;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import lombok.Data;

// @Data
//@Entity
public class Player {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    public boolean isDead(){
        if(hp < 0){
            return true;
        }
        return false;
    }

    // private final static MAX_CARDS = 10;
}
