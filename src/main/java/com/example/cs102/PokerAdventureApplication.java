package com.example.cs102;

import com.example.cs102.game.GameController;
import com.example.cs102.game.GameMenu;

public class PokerAdventureApplication {

    public static void main(String[] args) {
        GameController gameController = new GameController();
        GameMenu gameMenu = new GameMenu(gameController);
        gameMenu.readOption();
    }

}
