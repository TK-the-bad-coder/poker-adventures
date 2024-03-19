package com.example.cs102.Battle;
import com.example.cs102.player.Player;
import com.example.cs102.boss.Boss;
public class Battle{
    private Player player;
    private Boss boss;
    public Battle(Player player , Boss boss){
        this.player = player;
        this.boss = boss;
    }
    // the match function returns true if the player win, false if they die
    protected boolean match(){

        // initialising the current health of the player and the boss to their max health at the start of the match
        int playerCurrentHealth = player.getHp();
        int BossCurrentHealth = boss.getHp();

        while (playerCurrentHealth > 0 && BossCurrentHealth > 0 ){
            //while both havent die yet, play a turn
            
        }
        if (playerCurrentHealth >0){
            return true;
        }
        return false;
    }
    protected void turn(){

    }
}