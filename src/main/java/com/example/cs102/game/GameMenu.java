package com.example.cs102.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;
import java.util.Locale;

import com.example.cs102.exceptions.BossNotFoundException;
import com.example.cs102.exceptions.DuplicateCardException;
import com.example.cs102.exceptions.InsufficientGoldException;
import com.example.cs102.exceptions.InvalidHandException;
import com.example.cs102.exceptions.PlayerNotFoundException;
import com.example.cs102.exceptions.PotionNotFoundException;
import com.example.cs102.player.Player;
import com.example.cs102.potion.Potion;
import com.example.cs102.poker.BossDmgCalculatorUtility;
import com.example.cs102.poker.Card;
import com.example.cs102.poker.ComboUtility;
import com.example.cs102.poker.comparators.SuitComparator;
import com.example.cs102.poker.comparators.ValueComparator;
import com.example.cs102.boss.Boss;

public class GameMenu {
    private static final char SQUARE = '\u25a0';
    private static final String SPACE = " ";
    private static final int INDENTATION = 60;
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

            String input = "";
            System.out.print("Please enter your choice:");

            input = sc.nextLine();
            choice = 0;
            if (!input.isBlank()) {
                try {

                    choice = Integer.parseInt(input);
                    switch (choice) {
                        case 1:
                            start();
                            break;

                        case 2:
                            System.out.println("Bye Bye");
                            break;

                        default:
                            System.out.println("Enter a number 1 to 2");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number");
                    sc.next();
                }
            } else {
                System.out.println("Please enter something");
            }

        } while (choice != 2);
    }

    private void start() {
        Scanner sc = new Scanner(System.in);
        String name = "";
        boolean isValid = false;
        clearScreen();
        displayLogin();
        do {
            System.out.print(
                    SPACE.repeat(INDENTATION) + "Enter your player name (Case sensitive): ");
            name = sc.nextLine();
            if (name.isEmpty()) {
                System.out.println("Please enter something!!");
            }
            if (GameController.checkValidName(name)) {
                isValid = true;
            } else {
                System.out.println("Names can only be alphabetical, with no numbers or special symbols");
            }
        } while (!isValid);

        try {
            Player player = controller.login(name);
            clearScreen();
            gameMenu(player);
        } catch (PlayerNotFoundException e) {
            makeNewPlayer(name);
        }
    }

    private void makeNewPlayer(String name) {
        boolean isValid = true;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Would you like to make a new account? y/n");

            String input = sc.nextLine().toLowerCase(Locale.ENGLISH);

            switch (input) {
                case "":
                    System.out.println("Please enter something");
                    isValid = false;
                    break;
                case "n":
                    System.out.println("Ok, exiting to main menu.");
                    return;

                case "y":
                    Player player = this.controller.makeNewPlayer(name);
                    gameMenu(player);
                    break;

                default:
                    System.out.println("Please enter a valid input");
                    isValid = false;
                    break;
            }
        } while (!isValid);
    }

    private void gameMenu(Player player) {
        Scanner sc = new Scanner(System.in);
        String choice = "";
        clearScreen();
        welcome(player);
        do {
            displayMenuMessage();
            displayMenuOptions();
            System.out.print("Enter Choice of Menu:");

            choice = sc.nextLine();
            if (choice.isBlank()) {
                System.out.println("Please enter something");
            } else {
                try {
                    int menuOption = Integer.parseInt(choice);
                    switch (menuOption) {
                        case 1:
                            clearScreen();
                            controller.initPlayer(player);

                            selectBoss();
                            break;

                        case 2:
                            clearScreen();

                            displayShop();
                            welcomeShop(player);
                            controller.initPlayer(player);
                            selectShop();
                            break;

                        case 3:
                            return;

                        default:
                            System.out.println("Please enter a number between 1 and 3");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number");
                }
            }

        } while (true);

    }

    private void selectShop() {
        Scanner sc = new Scanner(System.in);
        Potion potion = null;
        String choice = "";
        do {
            showPotions();
            System.out.print("Enter Choice of Potion: ");
            choice = sc.nextLine().toLowerCase(Locale.ENGLISH);
            if ("e".equals(choice)) {
                System.out.println("Returning to main menu");
                return;
            }
            try {
                int potionChoice = Integer.parseInt(choice);
                potion = this.controller.selectPotion(potionChoice);
                boolean confirmed = confirmSelection(potion);
                if (confirmed) {
                    controller.purchasePotion(potion.getHp(), potion.getGold());
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            } catch (PotionNotFoundException e) {
                System.out.println("Please enter a number between 1 and 5");
            } catch (InsufficientGoldException e) {
                System.out.println("Insufficent Gold, please enter another Potion");
            }

        } while (true);

    }

    private void selectBoss() {
        Scanner sc = new Scanner(System.in);
        Boss boss = null;
        String choice = "";
        displayPlayerVsBoss();
        do {
            showBosses();
            System.out.print("Choose Your Boss: ");
            choice = sc.nextLine().toLowerCase(Locale.ENGLISH);
            if (choice.isBlank()) {
                System.out.println("Please enter something:");
            }
            if ("e".equals(choice)) {
                return;
            }
            try {
                int bossChoice = Integer.parseInt(choice);
                boss = this.controller.selectBoss(bossChoice);
                controller.initBoss(boss);
                startGame();
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            } catch (BossNotFoundException e) {
                System.out.println("Please enter a number bewteen 1 and 3");
            }

        } while (true);

    }

    private void showBosses() {
        List<Boss> bosses = this.controller.loadBosses();
        for (Boss boss : bosses) {
            System.out.println(boss);
        }
        System.out.println("To exit - PRESS e");
    }

    private void showPotions() {
        List<Potion> potions = this.controller.loadPotions();

        for (Potion p : potions) {
            System.out.println("" + p.getId() + ")" + " " + p.getItemName() + " (gold " + p.getGold() + ") : Increase "
                    + p.getHp() + "HP - PRESS " + p.getId());
        }
        System.out.println("e) Exit");
    }

    private void startGame() {
        controller.startGame();
        while (!controller.hasFled()) {

            displayBoss();
            playerTurn();
            if (controller.hasFled()) {
                return;
            }
            if (controller.getGameState().isBossDead()) {
                controller.increaseGold();
                System.out.printf("Congratulations! You Beat %s! \r\n",
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

    private void playerTurn() {
        Scanner sc = new Scanner(System.in);
        boolean confirmed = false;
        do {
            showGameState(controller.getGameState());

            List<Card> playerHand = controller.getPlayer().getHand().getCards();
            playerHand.sort(preferredComparator);
            showHand(playerHand);

            // asking player for input
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
                    controller.flee();
                    clearScreen();
                    System.out.printf("%s laughs at you as you flee to the main menu...",
                            controller.getBoss().getName());
                    System.out.println();
                    return;

                default:
                    String[] splittedCards = input.split(" ");

                    try {
                        // converting String[] cards to int[]
                        int[] intInput = Arrays.stream(splittedCards)
                                .mapToInt(number -> Integer.parseInt(number))
                                .toArray();

                        // Check player hand
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
                    break;
            }
        } while (!confirmed);
    }

    private void bossTurn() {
        displayBoss();
        System.out.println("");
        System.out.println(
                "================================================BOSS TURN===================================================");
        System.out.println("");
        System.out.println("Boss is picking his cards");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        clearScreen();
        // displayBoss();
        List<Card> combo = controller.bossMove();
        showBossMove(combo);
        controller.bossAttack(combo);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }

    private void showGameState(GameState gameState) {
        int playerCurrentHp = gameState.getPlayerCurrentHp();
        int bossCurrentHp = gameState.getBossCurrentHp();

        gameState.showPlayerHealth();
        showHealthBar(playerCurrentHp, controller.getPlayer().getHp());

        System.out.println("");

        gameState.showBossHealth();
        bossSpaces();
        showHealthBar(bossCurrentHp, controller.getBoss().getHp());
    }

    // showing
    // cards--------------------------------------------------------------------------------------------------
    private void showHand(List<Card> currentHand) {
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

        System.out.println(row1);
        System.out.println(row2);

        System.out.println("Enter 's' to sort by suit");
        System.out.println("Enter 'v' to sort by value");
        System.out.println("Enter 'f' to flee");
    }

    // displaying game
    // states------------------------------------------------------------------------------------------
    private void showHealthBar(int hp, int maxHp) {
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

    private void bossSpaces() {
        System.out.print(
                "                                                                                                                     ");
    }

    private boolean confirmSelection(Potion potion) {
        Scanner sc = new Scanner(System.in);
        while (true) {

            System.out.printf("You selected a %s\r\n", potion.getItemName());
            System.out.println("Confirm? y/n");
            String input = sc.nextLine().toLowerCase(Locale.ENGLISH);
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

    private boolean confirmSelection(List<Card> selectedCards) {
        Scanner sc = new Scanner(System.in);
        String value = ComboUtility.getHandValue(selectedCards);
        while (true) {
            showDiscards(selectedCards);
            System.out.printf("You selected a %s\r\n", value);
            System.out.println("Confirm? y/n");
            String input = sc.nextLine().toLowerCase(Locale.ENGLISH);
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

    private void showComboPlayed(String comboPlayed) {
        System.out.print("You have made the following move - " + comboPlayed + "\r\n");
    }

    private void showBossMove(List<Card> combo) {
        String comboValue = ComboUtility.getHandValue(combo);
        int damage = BossDmgCalculatorUtility.bossDamageCalculation(comboValue, controller.getBoss());
        System.out.printf("%s played a %s , and dealt %d damage\r\n", controller.getBoss().getName(), comboValue,
                damage);
    }

    private static void printTxt(String filename) {
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
            }

        } catch (FileNotFoundException e) {
            System.out.println("WHO DELETED MY FILEEE!!!");
        }
    }

    private void displayBoss() {
        int id = controller.getBoss().getId();
        switch (id) {
            case 1:
                printTxt("Images/playervsDemon.txt");
                break;

            case 2:
                printTxt("Images/playervsGrim.txt");
                break;

            case 3:
                printTxt("Images/playervsX.txt");
                break;

            default:
                break;
        }
    }

    // FOR DISPLAYING
    private void displayIntro() {
        printTxt("Images/pokerStart.txt");

        System.out.println("");
        displayMenuMessage();
        System.out.println(
                "                                                      Start Game[1]                        Quit App[2]");

    }

    private void displayLogin() {
        System.out.println(
                "=====================================================================================================================================================================");
        System.out.println(
                "-----------------------------------------------------------------------------Login-----------------------------------------------------------------------------------");
        System.out.println(
                "=====================================================================================================================================================================");

    }

    private void displayMenuMessage() {
        System.out.println(
                "-----------------------------------------------------------------------------Menu------------------------------------------------------------------------------------");
    }

    private void welcome(Player player) {
        System.out.println(
                "=====================================================================================================================================================================");
        System.out.printf(
                "                                                                Welcome to Poker Adventure, %s!\r\n",
                player.getName());
    }

    private void welcomeShop(Player player) {
        System.out.println(
                "=====================================================================================================================================================================");
        System.out.printf(
                "                                                              Welcome to Poker Adventure Shop,  %s!\r\n",
                player.getName());
        System.out.println("");
        System.out.println(player.toString());
        System.out.println("");
    }

    private void displayMenuOptions() {
        System.out.println(
                "                            To go on an Adventure[1]                        Shop[2]                        Log Out[3]");
    }

    private void displayPlayerVsBoss() {
        System.out.println(
                "=================================================================================================================================================================================");
        System.out.println(
                "-----------------------------------------------------------------------------Player vs Boss--------------------------------------------------------------------------------------");
        System.out.println(
                "=================================================================================================================================================================================");
    }

    private void displayShop() {
        System.out.println("..._____________|__|_...");
        System.out.println("../                  \\..");
        System.out.println("./ YL's potion Shop   \\..");
        System.out.println("/______________________\\");
        System.out.println(".|        ___         |.");
        System.out.println(".|  [ ]  |   |  [ ]   |.");
        System.out.println(".|_______|__'|________|.");
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
