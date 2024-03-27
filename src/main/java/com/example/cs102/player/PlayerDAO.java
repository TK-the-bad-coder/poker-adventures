package com.example.cs102.player;

import java.io.*;
import java.util.ArrayList;



import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

import java.util.Scanner;

public class PlayerDAO {

    private List<Player> players = new ArrayList<>();

    public PlayerDAO() {
        load();
    }

    private void load() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("data/players.csv"));

            while (sc.hasNextLine()) {
                String lineRead = sc.nextLine();
                Scanner lineReader = new Scanner(lineRead);
                lineReader.useDelimiter(",|\n|\r\n");
                if (!lineRead.isBlank()){
                    players.add(new Player(lineReader.nextInt(), lineReader.next(), lineReader.nextInt(), lineReader.nextInt()));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Shag, file maybe not found ah...");
        } catch (InputMismatchException e) {
            System.out.println("An error has occured when loading players"); // to raise exception and let menu handle
        } finally {
            if (sc != null){
                sc.close();
            }

        }
    }


    // returns the whole ArrayList of Player objects
    public List<Player> retrievePlayers() {
        return players;
    }

    // returns a single player object based on name,
    // or null if there is no matching player
    public Player retrieve(String name) {
       
        return players.stream()
                    .filter(player -> player.getName().equals(name))
                    .findFirst().orElse(null);
    }

    // adds a new player into playerList with a name
    // the ID of this new player is automatically generated
    public void addPlayer(String name) {
        int nextId = players.size() + 1;
        Player added = new Player(nextId,name);
        players.add(added);
        try (PrintStream out = new PrintStream(new FileOutputStream("data/players.csv", true))){
            out.printf("%d,%s,%d,%d\r\n", added.getId() , added.getName() , added.getHp(), added.getGold());
        } catch (FileNotFoundException e){
             System.out.println("Error with adding player name");
        }
    }

    public void save(String name, int newHp, int newGold) {
        Player p = retrieve(name);
    
        try (RandomAccessFile file = new RandomAccessFile("data/players.csv", "rw")){
            String line;
            while ((line = file.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[1].equals(p.getName())) {
                    parts[0] = Integer.toString(p.getId());
                    parts[1] = p.getName();
                    parts[2] = Integer.toString(newHp);
                    parts[3] = Integer.toString(newGold);
                    line = String.join(",", parts);
                    // Seek to the start of the line and overwrite it with the updated information
                    file.seek(file.getFilePointer() - line.length() - 2); // -2 to account for CRLF characters
                    file.writeBytes(line + "\r\n");
                    break;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        } catch (IOException e){
            System.out.println("Error saving player");
        }
        
        
    }

    public void saveAfterBattle(String name, int newGold) {
        Player p = retrieve(name);
    
        try (RandomAccessFile file = new RandomAccessFile("data/players.csv", "rw")){
            String line;
            while ((line = file.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[1].equals(p.getName())) {
                    parts[0] = Integer.toString(p.getId());
                    parts[1] = p.getName();
                    parts[3] = Integer.toString(newGold);
                    line = String.join(",", parts);
                    // Seek to the start of the line and overwrite it with the updated information
                    file.seek(file.getFilePointer() - line.length() - 2); // -2 to account for CRLF characters
                    file.writeBytes(line + "\r\n");
                    break;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        } catch (IOException e){
            System.out.println("Error saving player");
        }
        
        
    }
}
