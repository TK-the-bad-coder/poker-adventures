package com.example.cs102.game;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

import com.example.cs102.Exceptions.BossNotFoundException;
import com.example.cs102.Exceptions.PlayerNotFoundException;
import com.example.cs102.player.Player;
import com.example.cs102.boss.Boss;
public class GameMenu {
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
        try{
            Player player = this.controller.login(name);
            welcome(player);
        }
        catch (PlayerNotFoundException e){
            makeNewPlayer(name);
        }

    }
        // make deck for player

        // make deck for enemy

        // shuffle the decks

    

    public void makeNewPlayer(String name){
        boolean isValid = true;
        do {
            System.out.println("Would you like to make a new account? y/n");
            Scanner sc = new Scanner(System.in);
            String input = sc.next().toLowerCase();

            if (input.equals("n")){
                System.out.println("Ok, exiting to main menu.");
            } else if (input.equals("y")){
                Player player = this.controller.makeNewPlayer(name);
                welcome(player);
            } else {
                isValid = false;
                System.out.println("Please enter a valid input");
            }
        } while(!isValid);
    }


    public void welcome(Player player){
        System.out.println("===================================");
        System.out.printf("Welcome to Poker Adventure, %s!\r\n" , player.getName());
        
        
        boolean wantTutorial=GameTutorial.selectTutorial();
        if(wantTutorial){
            System.out.println("tutorial starts now");
            
            GameTutorial.actualTutorial();

            System.out.println("Time for the real deal, good luck!");
            System.out.println("===================================");
            System.out.println();
        }
        
        selectBoss(player);
        
        
    }

    public void selectBoss(Player player){
        Scanner sc = new Scanner(System.in);
        boolean isValid = false;
        Boss boss=null;
        do{
            showBosses();
            System.out.println("Enter Choice of opponent:");
            try{
                int choice = Integer.parseInt(sc.next());
                boss = this.controller.selectBoss(choice);
                isValid = true;
            } catch (NumberFormatException e){
                System.out.println("Please enter a number");
            } catch (BossNotFoundException e){
                System.out.println("Please enter a valid id");
            }

        } while (!isValid);

        controller.startGame(boss,player);
    }


    public void showBosses(){
        List <Boss> bosses = this.controller.loadBosses();
        for (Boss boss : bosses){
            System.out.println(boss);
        }
    }
}
