package com.example.cs102.game;

import java.util.InputMismatchException;
import java.util.Scanner;

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

        this.controller.loadPlayer(name);
        // make deck for player

        // make deck for enemy

        // shuffle the decks

    }

    public void displayGame() {
        System.out.println("=======================================");
        System.out.println("Choose your character!");
        System.out.println("=======================================");
        System.out.println("1: Yeow Leong");
        System.out.println("2: Lay Foo");
        System.out.println("3: Quit App");
        System.out.print("Please enter your choice: ");
    }

}
