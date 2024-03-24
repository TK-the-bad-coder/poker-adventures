package com.example.cs102.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.util.Locale;

import com.example.cs102.Comparators.SuitComparator;
import com.example.cs102.Comparators.ValueComparator;
import com.example.cs102.Exceptions.BossNotFoundException;
import com.example.cs102.Exceptions.DuplicateCardException;
import com.example.cs102.Exceptions.InvalidHandException;
import com.example.cs102.Exceptions.PlayerNotFoundException;
import com.example.cs102.player.Player;
import com.example.cs102.poker.Card;
import com.example.cs102.poker.Combo;
import com.example.cs102.poker.Deck;
import com.example.cs102.poker.DeckController;
import com.example.cs102.boss.Boss;
import com.example.cs102.hand.BossHand;
import com.example.cs102.hand.PlayerHand;

public class GameMenu {
    private static final char SQUARE = '\u25a0';
    private static final SuitComparator SC = new SuitComparator();
    private static final ValueComparator VC = new ValueComparator();
    private GameController controller;

    public GameMenu(GameController controller) {
        this.controller = controller;
    }

    public void readOption() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            displayIntro();
            choice = 0;
            try {
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        start();
                        break;

                    case 2:
                        System.out.println("Bye Bye");
                        break;

                    default:
                        System.out.println("Enter a number 1 or 2");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number");
                sc.next();
            }
        } while (choice != 2);
    }

    public void displayIntro() {
        System.out.println("=======================================");
        System.out.println("Welcome to Poker Adventure!");
        System.out.println("=======================================");
        System.out.println("1: Start Game");
        System.out.println("2: Quit App");
        System.out.print("Please enter your choice: ");
        return;
    }

    public void start() {

        Scanner sc = new Scanner(System.in);
        String name = "";

        do {
            System.out.println("=======================================");
            System.out.println("Enter your player name!");
            System.out.println("=======================================");
            name = sc.nextLine();
            if (name.isEmpty()) {
                System.out.println("Please enter something!!");
            }
        } while (name.isEmpty());
        // should retrieve whether the player exists or not.... do later
        try {
            Player player = this.controller.login(name);

            welcome(player);
        } catch (PlayerNotFoundException e) {
            makeNewPlayer(name);
        }
        return;
    }
    // make deck for player

    // make deck for enemy

    // shuffle the decks

    public void makeNewPlayer(String name) {
        boolean isValid = true;
        do {
            System.out.println("Would you like to make a new account? y/n");
            Scanner sc = new Scanner(System.in);
            String input = sc.next().toLowerCase();

            if (input.equals("n")) {
                System.out.println("Ok, exiting to main menu.");
            } else if (input.equals("y")) {
                Player player = this.controller.makeNewPlayer(name);
                welcome(player);
            } else {
                isValid = false;
                System.out.println("Please enter a valid input");
            }
        } while (!isValid);
    }

    public void welcome(Player player) {
        System.out.println("===================================");
        System.out.printf("Welcome to Poker Adventure, %s!\r\n", player.getName());
        controller.initPlayer(player);
        selectBoss();
        return;
    }

    public void selectBoss() {
        Scanner sc = new Scanner(System.in);
        boolean isValid = false;
        Boss boss = null;
        do {
            showBosses();
            System.out.println("Enter Choice of opponent:");
            String choice = sc.next().toLowerCase(Locale.ENGLISH);
            if (choice.equals("b")) {
                return;
            }
            try {
                int bossChoice = Integer.parseInt(choice);
                boss = this.controller.selectBoss(bossChoice);
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            } catch (BossNotFoundException e) {
                System.out.println("Please enter a valid choice");
            }

        } while (!isValid);
        controller.initBoss(boss);
        startGame();
    }

    public void showBosses() {
        List<Boss> bosses = this.controller.loadBosses();
        for (Boss boss : bosses) {
            System.out.println(boss);
        }
    }

    public void startGame() {
        Boss boss = controller.getBoss();
        Player player = controller.getPlayer();

        List<Card> cards = new ArrayList<>();
        DeckController deckControl = new DeckController(cards);
        cards = deckControl.initCards();

        Deck playerDeck = new Deck(new ArrayList<>(cards));
        Deck bossDeck = new Deck(new ArrayList<>(cards));

        player.setHand(new PlayerHand(playerDeck));
        boss.setHand(new BossHand(bossDeck));
        List<Card> currentHand = player.getCards();
        Scanner sc = new Scanner(System.in);

        GameState gameState = new GameState(player.getHp(), boss.getHp());
        int bossMaxHp = boss.getHp();
        int playerMaxHp = player.getHp();
        int bossHp = bossMaxHp;
        int playerHp = playerMaxHp;
        int damage = 0;
        Comparator<Card> displayChoice = VC;
        while (bossHp > 0 && playerHp > 0) {
            String input = "";
            boolean turnPlayed = false;
            do {
                currentHand.sort(displayChoice);
                showGameState(gameState);
                showHand(currentHand);
                requestPlayerCombo();
                input = sc.nextLine().toLowerCase(Locale.ENGLISH);

                switch (input) {
                    case "s":
                        displayChoice = SC;
                        break;
                    case "v":
                        displayChoice = VC;
                        break;
                    case "f":
                        System.out.println("The boss laughs at you as you flee to the main menu...");
                        System.out.println();
                        return;
                    default:
                        // continue on
                        if (input.isEmpty()) {
                            System.out.println("Please enter something");
                        } else {
                            String[] splittedCards = input.split(" ");

                            try {
                                int[] intInput = Arrays.stream(splittedCards)
                                        .mapToInt(number -> Integer.parseInt(number)).toArray();

                                // pass to controller
                                damage = controller.playerMove(intInput);
                                // the player played a turn
                                turnPlayed = true;
                                // show damage dealt
                                System.out.printf("You dealt %d damage to %s\r\n", damage, boss.getName());

                                // bossHp -= damage;
                                gameState.doDamageTo(boss, damage);
                                if (gameState.getBossCurrentHp() <= 0) {
                                    System.out.printf("Congratulations! You Beat %s! Have a cookie!\r\n",
                                            boss.getName());
                                    return;
                                }

                                // boss damage
                                damage = controller.bossMove(damage);

                                // update the currentHand on menu display
                                currentHand = player.getCards();

                                gameState.doDamageTo(player, damage);
                                System.out.printf("%s did %d damage to you\n", boss.getName(), damage);
                                if (gameState.getPlayerCurrentHp() <= 0) {
                                    System.out.printf("%s laughs over your wounded body", boss.getName());
                                    return;
                                }

                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Um... you dont have that many cards ah");

                            } catch (DuplicateCardException e) {
                                System.out.println(e.getMessage());
                            }

                            catch (InvalidHandException e) {
                                System.out.println("Hand should only contain either 1, 2 or 5 cards");
                                System.out.println(e.getMessage());
                                InvalidHandException.showValidChoices();
                            } catch (NumberFormatException e) {
                                System.out.println("|ERROR| Please enter a valid input");
                                InvalidHandException.showValidChoices();
                            } catch (IllegalArgumentException e) {
                                System.out.println("|ERROR| " + e.getMessage());
                            }
                        }
                }

            } while (!turnPlayed);

        }
    }

    public void showGameState(GameState gameState) {
        Boss boss = gameState.getBoss();
        Player player = gameState.getPlayer();
        int playerCurrentHp = gameState.getPlayerCurrentHp();
        int playerMaxHp = player.getHp();

        int bossCurrentHp = gameState.getBossCurrentHp();
        int bossMaxHp = boss.getHp();

        System.out.println("=======================================");
        System.out.println(player.getName() + ":");
        System.out.println("Health: " + playerCurrentHp + "/" + playerMaxHp);
        showHealthBar(playerCurrentHp, playerMaxHp);

        System.out.println("=======================================");
        System.out.println(boss.getName() + ":");
        System.out.println("Health: " + bossCurrentHp + "/" + bossMaxHp);
        showHealthBar(bossCurrentHp, bossMaxHp);
        System.out.println("=======================================");
    }

    // showing
    // cards--------------------------------------------------------------------------------------------------
    public void showHand(List<Card> currentHand) {
        String row1 = "";
        String row2 = "";
        int index = 0;
        for (Card card : currentHand) {
            row1 += "|" + card.getSpecialOutput();
            row2 += "| " + index + " ";
            index++;
        }
        row1 += "|";
        row2 += "|";
        // System.out.print("|\n");
        // System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |");
        System.out.println(row1);
        System.out.println(row2);
        System.out.println("Enter 's' to sort by suit");
        System.out.println("Enter 'v' to sort by value");
        System.out.println("Enter 'f' to flee");
    }

    public void showPlayedHand(List<Card> hand) {
        System.out.println("You played the following hand:");
        showDiscards(hand);

    }

    // displaying game
    // states------------------------------------------------------------------------------------------
    public void showHealthBar(int hp, int maxHp) {
        System.out.print("|");
        int numBars = hp * 30 / maxHp;
        for (int i = 0; i < 30; i++) {
            if (i < numBars) {
                System.out.print(SQUARE);
            } else {
                System.out.print(" ");
            }
        }
        System.out.println("|");
    }

    private void requestPlayerCombo() {
        System.out.println("======================================================");
        System.out.println("Select your card choice(s): ");
        System.out.println("======================================================");
        System.out.print("Cards (separate it by a space)> ");
    }

    private void showDiscards(List<Card> cards) {
        for (Card card : cards) {
            System.out.print("|");
            System.out.print(card.getSpecialOutput());
        }
        System.out.println("|");
    }
}
