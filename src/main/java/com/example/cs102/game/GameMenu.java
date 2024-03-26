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
import com.example.cs102.poker.ComboUtility;
import com.example.cs102.poker.Deck;
import com.example.cs102.poker.DeckController;
import com.example.cs102.boss.Boss;
import com.example.cs102.hand.BossHand;
import com.example.cs102.hand.PlayerHand;

public class GameMenu {
    private static final char SQUARE = '\u25a0';
    private static final SuitComparator SC = new SuitComparator();
    private static final ValueComparator VC = new ValueComparator();
    private Comparator<Card> preferredComparator = VC;
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
                        System.out.ntln("Bye Bye");
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
    }
    // make deck for player

    // make deck for enemy

    // shuffle the decks

    public void makeNewPlayer(String name) {
        boolean isValid = true;
        do {
            System.out.println("Would you like to make a new account? y/n");
            Scanner sc = new Scanner(System.in);
            String input = sc.next().toLowerCase(Locale.ENGLISH);

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
    }

    public void selectBoss() {
        Scanner sc = new Scanner(System.in);
        Boss boss = null;
        String choice = "";
        do {
            showBosses();
            System.out.println("Enter Choice of opponent:");
            choice = sc.next().toLowerCase(Locale.ENGLISH);
            if ("b".equals(choice)) {
                break;
            }
            try {
                int bossChoice = Integer.parseInt(choice);
                boss = this.controller.selectBoss(bossChoice);
                controller.initBoss(boss);
                startGame();
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            } catch (BossNotFoundException e) {
                System.out.println("Please enter a valid choice");
            }

        } while (!("b".equals(choice)));

    }

    public void showBosses() {
        List<Boss> bosses = this.controller.loadBosses();
        for (Boss boss : bosses) {
            System.out.println(boss);
        }
    }

    public void startGame() {
        controller.startGame();
        while (true) {
            playerTurn();
            if (controller.getGameState().isBossDead()) {
                System.out.printf("Congratulations! You Beat %s! Have a cookie!\r\n",
                        controller.getBoss().getName());
                return;
            }
            bossTurn();
            if (controller.getGameState().isPlayerDead()) {
                System.out.printf("%s laughs over your demise.....\r\n", controller.getBoss().getName());
                return;
            }
        }
    }

    public void playerTurn() {
        Scanner sc = new Scanner(System.in);
        boolean confirmed = false;
        do {
            showGameState(controller.getGameState());
            List<Card> playerHand = controller.getPlayer().getCards();
            playerHand.sort(preferredComparator);
            showHand(playerHand);
            requestPlayerCombo();
            String input = sc.nextLine().toLowerCase(Locale.ENGLISH);
            switch (input) {
                case "":
                    System.out.println("Please enter something");
                    break;
                case "s":
                    preferredComparator = SC;
                    break;

                case "v":
                    preferredComparator = VC;
                    break;

                case "f":
                    controller.getGameState().flee();
                    System.out.println("The boss laughs at you as you flee to the main menu...");
                    System.out.println();
                    break;

                default:

                    String[] splittedCards = input.split(" ");
                    int[] intInput = Arrays.stream(splittedCards)
                            .mapToInt(number -> Integer.parseInt(number)).toArray();
                    try {

                        controller.checkMove(intInput);
                        List<Card> selectedCards = controller.playerMove(intInput);
                        confirmed = confirmSelection(selectedCards);

                        if (confirmed) {
                            String comboPlayed = ComboUtility.getHandValue(selectedCards);
                            showComboPlayed(comboPlayed);
                            controller.playCombo(selectedCards);
                            int damage = ComboUtility.getDamageValue(comboPlayed);
                            System.out.printf("You dealt %d damage to %s\r\n", damage, controller.getBoss().getName());
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Um... you dont have that many cards ah");
                    } catch (DuplicateCardException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidHandException e) {
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
        } while (!confirmed);
    }

    public void bossTurn() {
        List<Card> combo = controller.bossMove();
        showBossMove(combo);
        controller.bossMove(combo);
    }

    public void showGameState(GameState gameState) {
        int playerCurrentHp = gameState.getPlayerCurrentHp();
        int bossCurrentHp = gameState.getBossCurrentHp();

        gameState.showPlayerHealth();
        showHealthBar(playerCurrentHp, controller.getPlayer().getHp());
        System.out.println("=======================================");
        gameState.showBossHealth();
        showHealthBar(bossCurrentHp, controller.getBoss().getHp());
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

    private boolean confirmSelection(List<Card> selectedCards) {
        Scanner sc = new Scanner(System.in);
        String value = ComboUtility.getHandValue(selectedCards);
        while (true) {
            showDiscards(selectedCards);
            System.out.printf("You selected a %s\r\n", value);
            System.out.println("Confirm? y/n");
            String input = sc.next().toLowerCase(Locale.ENGLISH);
            switch (input) {
                case "y":
                    return true;
                case "n":
                    return false;
                default:
                    System.out.println("Please enter a valid input");
                    break;
            }
        }
    }

    public void showComboPlayed(String comboPlayed) {
        System.out.print("You have made the following move - " + comboPlayed + "\r\n");
    }

    public void showBossMove(List<Card> combo) {
        String comboValue = ComboUtility.getHandValue(combo);
        System.out.printf("%s played a %s , and dealt %d damage\r\n", controller.getBoss().getName(), comboValue,
                ComboUtility.getDamageValue(comboValue));
    }
}
