package com.example.cs102.boss;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

// @Service
public class BossDAO {

    private List<Boss> bosses = new ArrayList<>();

    public BossDAO() {
        load();
    }

    private void load() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("data/bosses.csv"));
            sc.useDelimiter(",|\n|\r\n");
            while (sc.hasNext()) {
                // System.out.println(sc.next());
                bosses.add(new Boss(sc.nextInt(), sc.next(),sc.nextDouble(), sc.nextInt()));
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

    // returns the whole ArrayList of Player objects
    public List<Boss> retrieveBosses() {
        return bosses;
    }

    // returns a single boss object based on name,
    public Boss retrieve(String name) {
       
        Optional<Boss> selectedBoss = bosses.stream().filter(boss -> boss.getName().equals(name)).findFirst();

        return selectedBoss.orElse(null);
    }

}