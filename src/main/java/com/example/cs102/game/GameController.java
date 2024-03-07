package com.example.cs102.game;

import java.util.ArrayList;

//import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.example.cs102.boss.Boss;
import com.example.cs102.boss.BossDAO;
import com.example.cs102.hand.Hand;
import com.example.cs102.player.Player;
import com.example.cs102.player.PlayerDAO;
import com.example.cs102.poker.Card;
import com.example.cs102.poker.Deck;
import com.example.cs102.poker.DeckController;

public class GameController {

    // private final GameService service;
    private PlayerDAO playerDAO;
    private BossDAO bossDAO;

    public GameController() {
        // this.service = service;
        playerDAO = new PlayerDAO();
        bossDAO= new BossDAO();
    }

    public void readOption() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            display();
            choice = 0;
            try {
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        start("abc");
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

    public void display() {
        System.out.println("=======================================");
        System.out.println("Welcome to Poker Adventure!");
        System.out.println("=======================================");
        System.out.println("1: Start Game");
        System.out.println("2: Quit App");
        System.out.print("Please enter your choice: ");
    }

    public void displayPlayers() {
        // playerDAO // may not be used
    }

    public void start(String name) {
        
        // List<Player> players = playerDAO.retrievePlayers();
        Player player = playerDAO.retrieve(name); // assuming the name exists!
        // System.out.println(player);
        System.out.printf("Welcome, %s!\n", player.getName());
        

        //select boss
        Boss selectedBoss=selectBoss(bossDAO);
        
      


        List<Card> cards = new ArrayList<>();
        // new dc every time a game begins!
        DeckController deckControl = new DeckController(cards); 
        cards = deckControl.initCards();
        // ArrayList<Card> cards = deckControl.getCards();
    
        // make deck for player
        Deck playerDeck = new Deck(new ArrayList<>(cards));
        // playerDeck.getCards();

        // these are meant for debugging 
        // System.out.println("Player Cards remaining: " + playerDeck.getDeckLength());
        // System.out.println(playerDeck.drawCard());
        // System.out.println("Player Cards remaining: " + playerDeck.getDeckLength());
        // make deck for enemy
        
        
        
        Deck bossDeck = new Deck(new ArrayList<>(cards));
        // // List<Card> bossCards = new ArrayList<>();
        System.out.println("Boss Cards remaining: " + bossDeck.getDeckLength());
        
        //Starting Hand for both player and boss

        //testing hand
        // Card bossDraw = bossDeck.drawCard();
        // System.out.println(bossDraw.getValue() + " of " + bossDraw.getSuit());
         Hand bossHand= new Hand(bossDeck);
        
        System.out.println("Boss Cards remaining: " + bossDeck.getDeckLength());

        System.out.println("Special Check for Players:" + playerDeck.getDeckLength());

        // shuffle the decks

        // TODO: once both decks are made, pass both decks into a game display method
        // toDoMethod(playerDeck, bossDeck);

    }

        //select boss, loops until user selects a valid difficulty
    public Boss selectBoss(BossDAO bossDAO){
            Scanner scanner = new Scanner(System.in);

            Boss selectedBoss=null;

            
            while (selectedBoss == null) {
            System.out.printf("Enter Difficulty:\n" );
            List<Boss> bosses=bossDAO.retrieveBosses();
            for (Boss boss: bosses){
                String bossName=boss.getName();
                String difficulty= boss.getDifficulty();
                System.out.println("Name: "+bossName+ " ,Difficulty = " + difficulty);
            };

            String userInput=scanner.next().toUpperCase();
            selectedBoss =bossDAO.retrieve(userInput);
                if (selectedBoss == null) {
                    System.out.println("Invalid difficulty. Please try again.\n");
                }   
            }
            
            return selectedBoss;

            }

        public Hand startingHand(Deck deck, Hand user){
            for(int i=0;i<10;i++){
                Card card = deck.drawCard();
                user.addToHand(card);
                System.out.print(card.getValue() + " of " + card.getSuit()+", ");
            }
            return user;
        }
}
