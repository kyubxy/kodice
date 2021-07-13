package com.epictofu;

// Handles methods to print information to the screen
public class View {
    // print scores and other game information
    public void printInformation(Model m)
    {
        Score score = m.score;
        int rolls = m.rolls;
        int numDice = m.numDice;

        System.out.println("Current score: " + Double.toString (score.getTotal()));
        System.out.println("U: " + Double.toString (score.u));
        System.out.println("M: " + Double.toString (score.m));
        System.out.println("C: " + Double.toString (score.c));
        System.out.println("Rolls left: " + Integer.toString (rolls));
        System.out.println("Number of rice: " + Integer.toString (numDice));
    }
}
