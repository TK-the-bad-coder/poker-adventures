package com.example.cs102.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.cs102.boss.Boss;
import com.example.cs102.boss.BossDAO;
import com.example.cs102.hand.BossHand;
import com.example.cs102.hand.Hand;
import com.example.cs102.hand.PlayerHand;
import com.example.cs102.player.Player;
import com.example.cs102.player.PlayerDAO;
import com.example.cs102.poker.BestHandUtility;
import com.example.cs102.poker.Card;
import com.example.cs102.poker.ComboUtility;
import com.example.cs102.poker.Deck;
import com.example.cs102.poker.DeckController;
import com.example.cs102.Exceptions.PlayerNotFoundException;
import com.example.cs102.Exceptions.BossNotFoundException;
import com.example.cs102.Exceptions.DuplicateCardException;
import com.example.cs102.Exceptions.InvalidHandException;

public class GameController {

    // private final GameService service;
    private PlayerDAO playerDAO;
    private BossDAO bossDAO;

    private Player player;
    private Boss boss;

    public GameController() {
        playerDAO = new PlayerDAO();
        bossDAO = new BossDAO();
    }

    // public void displayPlayers() {
    // // playerDAO // may not be used
    // }

    public Player login(String name) {
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
        if (boss == null) {
            throw new BossNotFoundException();
        }
        return boss;
    }

    public List<Boss> loadBosses() {
        return bossDAO.retrieveBosses();
    }

    // loading the player and boss into the controller
    public void initPlayer(Player player) {
        this.player = player;
    }

    public void initBoss(Boss boss) {
        this.boss = boss;
    }

    public Boss getBoss() {
        return boss;
    }

    public Player getPlayer() {
        return player;
    }

    public Player makeNewPlayer(String name) {
        this.playerDAO.addPlayer(name);
        return login(name);
    }

    public String bossMove() {
        // to translate to retrieve from Boss
        Hand bossHand = boss.getHand();
        List<Card> bossSelection = BestHandUtility.getBestHand(boss.getCards());

        bossHand.discard(bossSelection);
        bossHand.addToHand();
        return ComboUtility.getHandValue(bossSelection);
        // return handValue;
        
    }

    public int playTurn(String comboMove) {
        return ComboUtility.getDamageValue(comboMove);
    }

    public int bossTurn(String comboMove) {
        int baseDamage = 0;

        int comboDamage = ComboUtility.getDamageValue(comboMove);
        switch (boss.getDifficulty()) {
            case "EASY":
                baseDamage = 1;
                break;
            case "NORMAL":
                // aim for up to
                baseDamage = 5;
                break;
            case "HARD":
                baseDamage = 20;
                comboDamage *= 2;
                break;
            case "ASIAN":
                baseDamage = 50; // literally one hit KO regardless of hand for new players
                comboDamage *= 3;
                break;
            default:
                // unknown case
                break;
        }
        return baseDamage + comboDamage;
    }

    public String playerMove(int[] input) {
        PlayerHand playerHand = player.getHand();
        List<Card> currentHand = player.getCards();
        int handSize = input.length;
        // error checking

        if (handSize != 1 && handSize != 2 && handSize != 5) {
            throw new InvalidHandException("Please enter a valid hand length!");
        } else if (Arrays.stream(input).distinct().toArray().length != handSize) {
            throw new DuplicateCardException("Hand contains duplicate choices! Please ensure all numbers are unique.");
        } else if (Arrays.stream(input).filter(num -> num < 0 || num > 9).toArray().length > 0) {
            throw new IllegalArgumentException(
                    "Your input contains an invalid number! Please key in numbers only from 0 to 9");
        }
        List<Card> cardSelection = new ArrayList<>();
        // getting the cards selected
        for (int number : input) {
            cardSelection.add(currentHand.get(number));
        }

        // check the hand value

        // as long as no error is raised and reaches here successfully
        // discard cards
        playerHand.discard(cardSelection);
        
        // draw card
        playerHand.addToHand();

        
        return ComboUtility.getHandValue(cardSelection);
    }

}
