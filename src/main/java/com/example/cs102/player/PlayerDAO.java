package com.example.cs102.player;

import java.io.*;
import java.util.ArrayList;

// import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

// @Service
public class PlayerDAO {

    private List<Player> players = new ArrayList<>();

    public PlayerDAO() {
        load();
    }

    private void load() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("data/players.csv"));
            sc.useDelimiter(",|\n|\r\n");
            while (sc.hasNext()) {
                players.add(new Player(sc.nextInt(), sc.next(), sc.nextInt()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Shag, file maybe not found ah...");
        } catch (InputMismatchException e) {
            System.out.println("An error has occured"); // to raise exception and let menu handle
        } finally {
            if (sc != null)
                sc.close();
        }
    }

    // returns the whole ArrayList of Player objects
    public List<Player> retrievePlayers() {
        return players;
    }

    // returns a single player object based on name,
    // or null if there is no matching player
    public Player retrieve(String name) {
       
        Optional<Player> playerEl = players.stream().filter(player -> player.getName().equals(name)).findFirst();

        return playerEl.orElse(null);
    }

    // adds a new player into playerList with a name
    // the ID of this new player is automatically generated
    public void addPlayer(String name) {
        int nextId = players.size() + 1;
        Player added = new Player(nextId,name);
        players.add(added);
        try (PrintStream out = new PrintStream(new FileOutputStream("data/players.csv", true))){
            out.printf("%d,%s,%d\r\n", added.getId() , added.getName() , added.getHp());
        } catch (FileNotFoundException e){
             System.out.println("Error with adding player name");
        }
    }
}
