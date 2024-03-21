package com.example.cs102.game;

import java.util.ArrayList;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


import com.example.cs102.boss.Boss;
import com.example.cs102.boss.BossDAO;
import com.example.cs102.hand.BossHand;
import com.example.cs102.hand.Hand;
import com.example.cs102.hand.PlayerHand;
import com.example.cs102.player.Player;
import com.example.cs102.player.PlayerDAO;
import com.example.cs102.poker.Card;
import com.example.cs102.poker.Combo;
import com.example.cs102.poker.Deck;
import com.example.cs102.poker.DeckController;
import com.example.cs102.Exceptions.PlayerNotFoundException;
import com.example.cs102.Exceptions.BossNotFoundException;
public class GameController {

    // private final GameService service;
    private PlayerDAO playerDAO;
    private BossDAO bossDAO;

    private Player player;
    private Boss boss;

    public GameController() {
        // this.service = service;
        playerDAO = new PlayerDAO();
        bossDAO = new BossDAO();
    }

    public void displayPlayers() {
        // playerDAO // may not be used
    }

    public Player login(String name) {

        // List<Player> players = playerDAO.retrievePlayers();
        player = playerDAO.retrieve(name);
        // if the player is not in the database
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        return player;
    }

    // select boss, loops until user selects a valid difficulty
    public Boss selectBoss(int n) {

        Boss boss = bossDAO.retrieve(n);
        if (boss == null){
            throw new BossNotFoundException();
        }
        return boss;
        }


    public List<Boss> loadBosses(){
        return bossDAO.retrieveBosses();
    }

    //loading the player and boss into the controller
    public void initPlayer(Player player){
        this.player = player;
    }
    public void initBoss(Boss boss){
        this.boss = boss;
    }

    public Boss getBoss(){
        return boss;
    }
    public Player getPlayer(){
        return player;
    }

    public Player makeNewPlayer(String name) {
        this.playerDAO.addPlayer(name);
        return login(name);
    }

    public int bossMove(Hand bossHand, int playerDamage) {
        int discardSize = 1;
        List<Card> bossChoice = new ArrayList<>();
        if (playerDamage >= 15) {
            // boss will discard five card if possible!
            discardSize = 5;
        }

        switch (boss.getDifficulty()) {
            case "EASY":
                // aim for up to two pairs
                return 1;
            // break;
            case "NORMAL":
                // aim for up to
                break;
            case "HARD":
                break;
        }
        return Combo.damage(bossChoice);
    }
    public int playTurn(List <Card> played){
        return Combo.damage(played);
    }
}
