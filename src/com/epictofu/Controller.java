package com.epictofu;

import java.util.*;

// Contains general game functionality
public class Controller {
    Model model;
    View view;

    public Controller ()
    {
        model = new Model();
        view = new View();
    }

    // screen to show before the player rolls
    public void screenPreRoll ()
    {
        System.out.println();

        // game loop
        if (model.rolls >= 0) {
            System.out.println("Enter new line to roll..");

            Scanner keyboard = new Scanner(System.in);
            keyboard.nextLine();

            screenResults(model.roll());
        }
        else
        {
            screenGameOver();
        }
    }

    // print results
    public void screenResults (List<String> results)
    {
        System.out.println ();
        System.out.println ("Results");
        System.out.println (results);

        // calculate results?
        model.updateScore(results);
        model.applyMultipliers(results);
        model.updateRollsAndDice (results);

        // show results
        view.printInformation(model);
        screenPreRoll();
    }

    // screen to show when the player runs out of rolls
    public void screenGameOver()
    {
        System.out.println ("Game Over");
        view.printInformation(model);
        System.exit(0);
    }
}
