
package com.example.cs102.game;
import com.example.cs102.boss.*;
import java.util.Scanner;

public class GameTutorial {
    public static boolean selectTutorial(){

        System.out.printf("Do you want to play the tutorial? Y/N: ");
        //check if user input Y/N
        Scanner sc= new Scanner(System.in);
        String userInput=sc.next();
        userInput=userInput.toLowerCase();
        boolean invalidInput=true;

        if(userInput.equals("n")){
            return false;
        }

        while(invalidInput){
            if(userInput.equals("y")){
                invalidInput=false;
            }
            else{
                //if invalid run loop again
                System.err.println("Invalid input, please enter y/n");
                System.out.printf("Do you want to play the tutorial? Y/N: ");
                userInput=sc.next();
                userInput=userInput.toLowerCase();

                if(userInput.equals("N")){
                    return false;
                }
            }
        }
        return true;
    }


    public static void actualTutorial(){
        Boss tutorialBoss=new Boss("Common thug",0.1,15);

        GameController gameController=new GameController();
        gameController.startGame(tutorialBoss);

    }
}
