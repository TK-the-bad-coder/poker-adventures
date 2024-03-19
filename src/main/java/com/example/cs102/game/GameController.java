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

        return boss;
        }
    public List<Boss> loadBosses(){
        return bossDAO.retrieveBosses();
    }

    // gameDisplay method -- to move all the prints into menu
    // logic stays here
    public void startGame() {

        List<Card> cards = new ArrayList<>();
        // new dc every time a game begins!
        DeckController deckControl = new DeckController(cards); 
        cards = deckControl.initCards();
    
        Deck playerDeck = new Deck(new ArrayList<>(cards));

        Deck bossDeck = new Deck(new ArrayList<>(cards));

        PlayerHand playerHand = new PlayerHand(playerDeck);
        BossHand bossHand = new BossHand(bossDeck);
        List<Card> currentHand = playerHand.getHand();
        Scanner sc = new Scanner(System.in);

        int bossHP = boss.getHp();
        int playerHP = boss.getHp();
        // line 124 to line 131 should be in menu
        while (bossHP > 0 && playerHP > 0) {
            playerHand.showHand();
            do {
                // print logic goes to Menu
                // pass in cardChoice to another method
                // System.out.println("Type 'q' to flee!");
                System.out.println("======================================================");
                System.out.println("Select your card choice(s) below or type 'q' to flee: ");
                System.out.println("======================================================");
                System.out.print("Cards (separate it by a space)> ");
                cardsChoice = sc.nextLine();
                if (cardsChoice.isEmpty()) {
                    System.out.println("Please enter something!!");
                } else if (cardsChoice.equals("q")) {
                    System.out.println("The boss laughs at you as you flee back to the main menu...");
                    return;
                }
                // this should be a method to call the cards
                List<Card> out = new ArrayList<>();
                String[] splitted_cards = cardsChoice.split(" ");
                for (int i = 0; i < splitted_cards.length; i++) {
                    out.add(currentHand.get(Integer.parseInt(splitted_cards[i])));
                }
                // check if hand selection is correct anot
                if (Combo.damage(out) != 0) {
                    // if hand selection is correct, deal damage
                    int damage = Combo.damage(out);
                    System.out.println("You played the following hand:");
                    playerHand.discard(out);
                    
                    playerHand.addToHand();
                    // update the current hand
                    
                    currentHand = playerHand.getHand();
                    System.out.println("Your damage is " + damage);
                    // System.out.println("\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014");
                    System.out.println("=======================================");

                    bossHP -= damage;
                    bossMove(bossHand, damage);
                }
                // return the outcome to menu

            } while (cardsChoice.isEmpty());
        }

    }

    public Player makeNewPlayer(String name) {
        this.playerDAO.addPlayer(name);
        return login(name);
    }

    private int bossMove(Hand bossHand, int playerDamage) {
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
}
