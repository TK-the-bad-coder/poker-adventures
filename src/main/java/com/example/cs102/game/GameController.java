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
        Player player = playerDAO.retrieve(name); 
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
    public void gameDisplay(Hand playerHand) {
        List<Card> currentHand = playerHand.getHand();
        Scanner sc = new Scanner(System.in);
        String cardsChoice = "";
        // line 124 to line 131 should be in menu
        do {
            System.out.println("=======================================");
            System.out.println("Enter your card choice: ");
            System.out.println("=======================================");
            cardsChoice = sc.nextLine();
            if (cardsChoice.isEmpty()) {
                System.out.println("Please enter something!!");
            }
            List<Card> out = new ArrayList<>();
            String[] splitted_cards = cardsChoice.split(" ");
            for (int i = 0; i < splitted_cards.length; i++) {
                out.add(currentHand.get(Integer.parseInt(splitted_cards[i])));
            }
            // check if hand selection is correct anot
            if (Combo.damage(out) != 0) {
                // if hand selection is correct, deal damage
                int damage = Combo.damage(out);
                playerHand.discard(out);
                System.out.println("Your damage is " + damage); 

            }

        } while (cardsChoice.isEmpty());

    }

    public Player makeNewPlayer(String name) {
        this.playerDAO.addPlayer(name);
        return login(name);
    }
}
