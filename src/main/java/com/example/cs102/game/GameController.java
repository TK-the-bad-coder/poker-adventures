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

    public void loadPlayer(String name) {

        // List<Player> players = playerDAO.retrievePlayers();
        player = playerDAO.retrieve(name);
        // if the player is not in the database
        if (player == null) {
            if (!makeNewPlayer(name)) {
                return;
            }
            player = playerDAO.retrieve(name);
        }
        if (player != null) {
            startGame();
        }
    }

    public void startGame() {
        // select boss
        boss = selectBoss(bossDAO);

        List<Card> cards = new ArrayList<>();
        // new dc every time a game begins!
        DeckController deckControl = new DeckController(cards);
        cards = deckControl.initCards();

        // make deck for player
        Deck playerDeck = new Deck(new ArrayList<>(cards));
        // make deck for enemy
        Deck bossDeck = new Deck(new ArrayList<>(cards));

        // Starting Hand for both player and boss
        BossHand bossHand = new BossHand(bossDeck); // pass to boss

        System.out.println();
        PlayerHand playerHand = new PlayerHand(playerDeck);
        gameDisplay(playerHand, bossHand);
    }

    // select boss, loops until user selects a valid difficulty
    public Boss selectBoss(BossDAO bossDAO) {
        Scanner scanner = new Scanner(System.in);

        Boss selectedBoss = null;

        while (selectedBoss == null) {
            System.out.printf("Enter Difficulty:\n");
            List<Boss> bosses = bossDAO.retrieveBosses();
            for (int i = 0; i < bosses.size(); i++) {
                String bossName = bosses.get(i).getName();
                String difficulty = bosses.get(i).getDifficulty();
                int number = 1 + i;
                System.out
                        .println(number + ": Name: " + bossName + " ,Difficulty = " + difficulty + ". Press " + number);
            }
            System.out.print("Enter your choice: ");
            int userInput = scanner.nextInt();
            System.out.println();
            String difficultyString = translateDifficulty(userInput);
            selectedBoss = bossDAO.retrieve(difficultyString);
            if (selectedBoss == null) {
                System.out.println("Invalid difficulty. Please try again.\n");
            }
        }

        return selectedBoss;

    }

    public static String translateDifficulty(int userInput) {
        if (userInput == 1) {
            return "EASY";
        }
        if (userInput == 2) {
            return "NORMAL";
        }
        if (userInput == 3) {
            return "HARD";
        } else {
            return null;
        }

    }

    // gameDisplay method -- to move all the prints into menu
    // logic stays here
    public void gameDisplay(PlayerHand playerHand, BossHand bossHand) {
        List<Card> currentHand = playerHand.getHand();
        Scanner sc = new Scanner(System.in);
        String cardsChoice = "";

        int bossHP = boss.getHp();
        int playerHP = boss.getHp();
        // line 124 to line 131 should be in menu
        while (bossHP > 0 && playerHP > 0) {
            playerHand.showHand();
            do {
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

            } while (cardsChoice.isEmpty());
        }

    }

    public boolean makeNewPlayer(String name) {
        System.out.println("Player name not found");

        boolean isValid = false;
        do {
            System.out.println("Would you like to make a new account? y/n");
            Scanner sc1 = new Scanner(System.in);
            String input = sc1.next();
            if (input.equals("n") || input.equals("N")) {
                System.out.println("Ok, Bye Bye!");
                isValid = true;
            } else if (input.equals("y") || input.equals("Y")) {
                playerDAO.addPlayer(name);
                sc1.close();
                return true;

            } else {
                System.out.println("Please enter a valid input");
            }
        } while (!isValid);

        // sc1.close();

        return false;
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
