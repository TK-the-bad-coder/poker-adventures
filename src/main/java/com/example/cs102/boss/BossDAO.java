package com.example.cs102.boss;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.example.cs102.exceptions.BossNotFoundException;

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

                bosses.add(new Boss(sc.nextInt(), sc.next(), sc.nextInt(), sc.next(), sc.nextInt()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Shag, file maybe not found ah...");
        } catch (InputMismatchException e) {
            System.out.println("Something went wrong");
        } finally {
            if (sc != null) {
                sc.close();
            }

        }
    }

    // returns the whole ArrayList of Player objects
    public List<Boss> retrieveBosses() {
        return bosses;
    }

    // returns a single boss object based on difficulty selected,
    public Boss retrieve(int id) {
        List<Boss> bosses = retrieveBosses();
        return bosses.stream().filter(boss -> boss.getId() == id)
                .findFirst()
                .orElseThrow(BossNotFoundException::new);
    }

}