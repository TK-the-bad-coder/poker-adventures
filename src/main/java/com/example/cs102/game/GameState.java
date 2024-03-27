package com.example.cs102.game;


import com.example.cs102.boss.Boss;
import com.example.cs102.player.Player;

public class GameState {
    private int playerCurrentHp;

    private int bossCurrentHp;

    private Player player;
    private Boss boss;
    public GameState(Player player, Boss boss) {
        this.player = player;
        this.boss = boss;
        playerCurrentHp = player.getHp();
        bossCurrentHp = boss.getHp();

    }

    public Boss getBoss() {
        return boss;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerCurrentHp() {
        return playerCurrentHp;
    }

    public int getBossCurrentHp() {
        return bossCurrentHp;
    }


    // ---------------------------------------------------------------------------------
    public void doDamageTo(Object target, int damage) {
        if (target instanceof Player) {
            playerCurrentHp -= damage;
        }
        if (target instanceof Boss) {
            bossCurrentHp -= damage;
        }
    }
    public boolean isPlayerDead(){
        return playerCurrentHp <= 0;
    }
    public boolean isBossDead(){
        return bossCurrentHp <= 0;
    }
    
    //------------------------------------------------------------------------------------
    public void showPlayerHealth() {
        System.out.println("=======================================");
        System.out.println(player.getName() + ":");
        System.out.println("Health: " + playerCurrentHp + "/" + player.getHp());
    }

    public void showBossHealth(){
        System.out.println(boss.getName() + ":");
        System.out.println("Health: " + bossCurrentHp + "/" + boss.getHp());
        System.out.println("=======================================");
    }

    

}
