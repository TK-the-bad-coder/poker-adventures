package com.example.cs102.game;

import com.example.cs102.boss.Boss;
import com.example.cs102.player.Player;

public class GameState {
    private final int playerMaxHp;
    private int playerCurrentHp;
    private final int bossMaxHp;
    private int bossCurrentHp;

    private Player player;
    private Boss boss;

    public GameState(Player player, Boss boss) {
        // playerName = player.getName();
        playerMaxHp = player.getHp();
        playerCurrentHp = playerMaxHp;

        // bossName = boss.getName();
        bossMaxHp = boss.getHp();
        this.bossCurrentHp = bossMaxHp;
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

    public String checkGameProgress() {
        if (playerCurrentHp > 0 && bossCurrentHp > 0) {
            return "Game ongoing";
        }
        if (playerCurrentHp <= 0) {
            return "Player died";
        }
        return "Boss died";
    }

}
