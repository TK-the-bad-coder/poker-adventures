package com.example.cs102.potion;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class PotionDAO {

    private List<Potion> potions = new ArrayList<>();

    public PotionDAO() {
        load();
    }

    private void load() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("data/potion.csv"));
            sc.useDelimiter(",|\n|\r\n");
            while (sc.hasNext()) {
                potions.add(new Potion(sc.nextInt(),sc.next(),sc.nextInt(),sc.nextInt()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Shag, file maybe not found ah...");
        } catch (InputMismatchException e) {
            System.out.println("Something went wrong");
        } finally {
            if (sc != null)
                sc.close();
        }
    }

    // returns the whole ArrayList of potion objects
    public List<Potion> retrievePotion() {
        return potions;
    }

    // returns a single potion object based on potion selected,
    public Potion retrieve(int id) throws IndexOutOfBoundsException{
       List<Potion> potions = retrievePotion(); 

        return potions.get(id - 1);
    }

}