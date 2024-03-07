package com.example.cs102;

import com.example.cs102.game.GameController;
import com.example.cs102.game.GameMenu;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
public class PokerAdventureApplication {

    public static void main(String[] args) {
        GameController gameController = new GameController();
        GameMenu gameMenu = new GameMenu(gameController);
        gameMenu.readOption();
        
        // SpringApplication.run(PokerAdventureApplication.class, args);
    }

}
