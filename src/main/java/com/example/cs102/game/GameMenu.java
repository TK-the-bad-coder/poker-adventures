package com.example.cs102.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
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
import com.example.cs102.poker.Card;
import com.example.cs102.poker.ComboUtility;

import com.example.cs102.boss.Boss;

import com.example.cs102.comparators.SuitComparator;
import com.example.cs102.comparators.ValueComparator;


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
            System.out.print("Please enter your choice: ");
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

    public void start() {

        Scanner sc = new Scanner(System.in);
        String name = "";
        boolean isValid = false;
        clearScreen();
        displaylogin();
        do {
            System.out.print("                                                                    Enter your player name: ");
            name = sc.nextLine();
            if (name.isEmpty()) {
                System.out.println("Please enter something!!");
            }
            if (GameController.checkValidName(name)){
                isValid = true;
            } else {
                System.out.println("Names can only be alphabetical, with no numbers or special symbols");
            }
        } while (!isValid);

        try {
            Player player = controller.login(name);
            clearScreen();
            gamemenu(player);
        } catch (PlayerNotFoundException e) {
            makeNewPlayer(name);
        }
    }

    public void makeNewPlayer(String name) {
        boolean isValid = true;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Would you like to make a new account? y/n");

            String input = sc.next().toLowerCase(Locale.ENGLISH);

            switch(input){
                case "n":
                System.out.println("Ok, exiting to main menu.");
                return;

                case "y":
                Player player = this.controller.makeNewPlayer(name);
                gamemenu(player);
                break;

                default:
                System.out.println("Please enter a valid input");
                isValid = false;
                break;
            }
        } while (!isValid);
    }

    public void gamemenu(Player player){
        Scanner sc = new Scanner(System.in);
        String choice = "";
        clearScreen();
        welcome(player);


        do{
            displayMenuMessage();
            displayMenuOptions();
            System.out.print("Enter Choice of Menu:");
        
            choice = sc.next();

            try {
                int menuOption = Integer.parseInt(choice);
                switch(menuOption){
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
                    selectshop(player);
                    break;

                    case 3:
                    return;

                    default:
                        System.out.println("Please enter a number between 1 and 3");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        } while (true);
        
    }

    public void selectshop(Player player) {
        Scanner sc = new Scanner(System.in);
        Potion potion = null;
        String choice = "";
        do{
            showPotions();
            System.out.print("Enter Choice of Potion:");
            choice = sc.next().toLowerCase(Locale.ENGLISH);
            if ("e".equals(choice)) {
                System.out.println("Returning to main menu");
                return;
            }
            try {
                int potionChoice = Integer.parseInt(choice);
                potion = this.controller.selectPotion(potionChoice);
                controller.purchasePotion(potion.getHp(), potion.getGold());
                
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            } catch (PotionNotFoundException e){
                System.out.println("Please enter a valid Potion choice");
            } catch (InsufficientGoldException e){
                System.out.println("Insufficent Gold, please enter another Potion");
            } catch (IndexOutOfBoundsException e){
                System.out.println("The shop dont have so many things ah");
            }
            
        } while (true);

    }

    public void selectBoss() {
        Scanner sc = new Scanner(System.in);
        Boss boss = null;
        String choice = "";
        displayPlayerVsBoss();
        do {
            showBosses();
            System.out.println("Choose Your Boss: ");
            choice = sc.next().toLowerCase(Locale.ENGLISH);
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

    public void showBosses() {
        List<Boss> bosses = this.controller.loadBosses();
        for (Boss boss : bosses) {
            System.out.println(boss);
        }
        System.out.println("To exit - PRESS e");
    }

    public void showPotions() {
        List<Potion> potions = this.controller.loadPotions();

        for (Potion p : potions) {
            System.out.println("" + p.getId() + ")" + " " + p.getItemName() + " (gold " + p.getGold() + ") : Increase " + p.getHp() + "HP - PRESS " + p.getId() );
        }
        System.out.println("e) EXIT");
    }

    public void startGame() {
        controller.startGame();
        while (!controller.hasFled()) {

            displayBoss();
            playerTurn();
            if (controller.hasFled()){
                return;
            }
            if (controller.getGameState().isBossDead()) {
                controller.increaseGold();
                System.out.printf("Congratulations! You Beat %s! \r\n",
                        controller.getBoss().getName());
                return;
            }
            
            bossTurn();
            if (controller.getGameState().isPlayerDead()){
                System.out.printf("%s laughs over your demise.....\r\n" , controller.getBoss().getName());
                return;
            }
        }
    }

    public void playerTurn(){
        Scanner sc = new Scanner(System.in);
        boolean confirmed = false;
        do{
            showGameState(controller.getGameState());

            List<Card> playerHand = controller.getPlayer().getCards();
            playerHand.sort(preferredComparator);
            showHand(playerHand);

            // asking player for input
            requestPlayerCombo();

            String input = sc.nextLine().toLowerCase(Locale.ENGLISH);
            switch(input){
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
                System.out.println("The boss laughs at you as you flee to the main menu...");
                System.out.println();
                return;

            default:
                String[] splittedCards = input.split(" ");

                try{
                    // converting String[] cards to int[]
                    int[] intInput = Arrays.stream(splittedCards)
                                            .mapToInt(number -> Integer.parseInt(number))
                                            .toArray();

                    //Check player hand
                    controller.checkMove(intInput);

                    List<Card> selectedCards = controller.playerMove(intInput);
                    confirmed = confirmSelection(selectedCards);

                    if (confirmed){
                        String comboPlayed = ComboUtility.getHandValue(selectedCards);
                        showComboPlayed(comboPlayed);
                        controller.playCombo(selectedCards);
                        int damage = ComboUtility.getDamageValue(comboPlayed);
                        System.out.printf("You dealt %d damage to %s\r\n" , damage , controller.getBoss().getName());
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
        }while (!confirmed);
    }
    public void bossTurn(){
        displayBoss();
        System.out.println("");
        System.out.println("================================================BOSS TURN===================================================");
        System.out.println("");
        System.out.println("Boss is picking his cards");
        try{
            Thread.sleep(1500);
        }catch (InterruptedException e){
            System.out.println("Interrupted");
        }
        clearScreen();
        displayBoss();
        List<Card> combo = controller.bossMove();
        showBossMove(combo);
        controller.bossMove(combo);
        try{
            Thread.sleep(1500);
        }catch (InterruptedException e){
            System.out.println("Interrupted");
        }
    }


    public void showGameState(GameState gameState) {
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

    public void bossSpaces(){
        System.out.print("                                                                                                                     ");
    }

    private boolean confirmSelection(List <Card> selectedCards) {
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

    public void showComboPlayed(String comboPlayed){
        System.out.print("You have made the following move - " + comboPlayed + "\r\n");
    }

    public void showBossMove(List<Card> combo){
        String comboValue = ComboUtility.getHandValue(combo);
        int damage = controller.bossTurn(comboValue);
        System.out.printf("%s played a %s , and dealt %d damage\r\n" , controller.getBoss().getName() , comboValue , damage);
    }

    public static void printTxt(String filename) {
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("WHO DELETED MY FILEEE!!!");
        }
    }

    public void displayBoss(){
        int id = controller.getBoss().getId();
        switch (id) {
            case 1:
                printTxt("displayImg/playervsDemon.txt");
                break;
                
            case 2:
                printTxt("displayImg/playervsGrim.txt");
                break;
            
            case 3:
                printTxt("displayImg/playervsX.txt");
                break;

            default:
                break;
        }
    }

    // FOR DISPLAYING
    public void displayIntro() {
        displayPokerStart();

        System.out.println("");
        displayMenuMessage();
        System.out.println("                                                      Start Game[1]                        Quit App[2]");
        
    }

    public void displayPokerStart(){
        printTxt("displayImg/pokerStart.txt");
    }

    public void displaylogin(){
        System.out.println("=====================================================================================================================================================================");
        System.out.println("-----------------------------------------------------------------------------Login-----------------------------------------------------------------------------------");
        System.out.println("=====================================================================================================================================================================");
        
    }

    public void displayMenuMessage(){
        System.out.println("-----------------------------------------------------------------------------Menu------------------------------------------------------------------------------------");
    }

    public void welcome(Player player) {
        System.out.println("=====================================================================================================================================================================");
        System.out.printf("                                                                Welcome to Poker Adventure, %s!\r\n", player.getName());
    }

    public void welcomeShop(Player player){
        System.out.println("=====================================================================================================================================================================");
        System.out.printf("                                                              Welcome to Poker Adventure Shop,  %s!\r\n" , player.getName());
        System.out.println("");
        System.out.println(player.toString());
        System.out.println("");
    }

    public void displayMenuOptions(){
        System.out.println("                            To go on an Adventure[1]                        Shop[2]                        Exit[3]");
    }

    public void displayPlayerVsBoss(){
        System.out.println("=================================================================================================================================================================================");
        System.out.println("-----------------------------------------------------------------------------Player vs Boss--------------------------------------------------------------------------------------");
        System.out.println("================================================================================================================================================================================="); 
    }

    public void displayShop(){
        System.out.println("..._____________|__|_...");
        System.out.println("../                  \\..");
        System.out.println("./ YL's potion Shop   \\..");
        System.out.println("/______________________\\");
        System.out.println(".|        ___         |.");
        System.out.println(".|  [ ]  |   |  [ ]   |.");
        System.out.println(".|_______|__'|________|.");
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  

}
