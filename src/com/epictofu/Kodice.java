package com.epictofu;

import java.util.*;

public class Kodice {
    public boolean Debug = true;

    Score score = new Score(0);
    int numDice = 5;
    int rolls = 3;

    String[] dice = {"U", "CHI", "N", "O", "MA", "KO"};

    Map <String, Score> singleDiceScoring = new HashMap<String, Score>();
    Map <String, Score> wordsScoring = new HashMap<String, Score>();
    Map <String, Score> tripletScoring = new HashMap<String, Score>();
    Map <String, Score> quadScoring = new HashMap<String, Score>();

    Random rand;

    public Kodice ()
    {
        init();
        /*
        screenPreRoll();
        */

        String[] e = {"U", "U", "U"};
        screenResults(Arrays.asList(e));

        /*
        List <String> wh = new ArrayList<>();
        wh.add("U");
        wh.add("N");
        wh.add("MA");
        wh.add("KO");
        System.out.println (calculateDice(wh));
        */
    }

    // set up variables and scoring etc..
    void init()
    {
        rand = new Random();

        singleDiceScoring.put ("U", new Score (500,0,0));
        singleDiceScoring.put ("MA", new Score (0, 500,0));
        singleDiceScoring.put ("CHI", new Score(0,0,500));
        singleDiceScoring.put ("N", new Score(50));
        singleDiceScoring.put ("KO", new Score (100));
        singleDiceScoring.put ("O", new Score (30));

        wordsScoring.put ("UNCHI",new Score (1000, 0,  0));
        wordsScoring.put ("UNKO", new Score (1000, 0,  0));
        wordsScoring.put ("MANKO", new Score (0, 1000, 0));
        wordsScoring.put ("OMANKO",new Score (0, 5000, 0));
        wordsScoring.put ("CHINKO", new Score (0,0,1000));
        wordsScoring.put ("CHINCHIN", new Score (3000));

        tripletScoring.put ("U", new Score (2,0,0));
        tripletScoring.put ("MA", new Score (0, 2,0));
        tripletScoring.put ("CHI", new Score(0,0,2));
        tripletScoring.put ("N", new Score(-3));
        tripletScoring.put ("KO", new Score (1.5));
        tripletScoring.put ("O", new Score (1.5));

        quadScoring.put ("U", new Score (4,0,0));
        quadScoring.put ("MA", new Score (0, 4,0));
        quadScoring.put ("CHI", new Score(0,0,4));
        quadScoring.put ("N", new Score(-4));
        quadScoring.put ("KO", new Score (4));
        quadScoring.put ("O", new Score (4));
    }

    // print scores and other game information
    public void printInformation()
    {
        System.out.println("Current score: " + Double.toString (score.getTotal()));
        System.out.println("U: " + Double.toString (score.u));
        System.out.println("M: " + Double.toString (score.m));
        System.out.println("C: " + Double.toString (score.c));
        System.out.println("Rolls left: " + Integer.toString (rolls));
        System.out.println("Dice left: " + Integer.toString (numDice));
    }

    public void Debug (String msg)
    {
        if (Debug)
            System.out.println("DEBUG: " + msg);
    }

    // simulate a roll
    public void roll ()
    {
        List <String> results = new ArrayList<String>();
        Debug ("rolling");

        for (int i = 0; i < rolls; i++)
        {
            var result = dice[rand.nextInt(dice.length)];
            results.add(result);
        }
        // TODO: add piss
        screenResults(results);
    }

    // print results
    public void screenResults (List <String> results)
    {
        Debug("results " + results);

        score.addWith(calculateScore(results));
        applyMultipliers(results);

        int dice = calculateDice (results);
        numDice += dice;
        rolls += dice;

        screenPreRoll();
    }

    // calculate the score given results according to the scoring table
    public Score calculateScore (List <String> results)
    {
        Score scoreacc = new Score(0);

        // dice faces
        for (String working : results)
        {
            scoreacc.addWith (singleDiceScoring.get (working));
            Debug ("adding dice faces");
        }

        // words
        List <String> words = getAllWords(results);

        for (String word : words)
        {
            scoreacc.addWith (wordsScoring.get(word));
            Debug ("adding words");
        }

        Debug (scoreacc.getTotal().toString());

        return scoreacc;
    }

    // applies multipliers to the actual score
    // THIS FUNCTION CONTAINS SIDE EFFECTS! BEWARE!
    public void applyMultipliers (List <String> _results)
    {
        List <String> results = new ArrayList<String> (_results);

        // triplets (and 4s)
        for (String x : tripletScoring.keySet())
        {
            List <String> copy = new ArrayList<String>(results);
            copy.removeIf (y -> y.equals(x));

            if (copy.size() == 3)
            {
                Debug ("triple");
                var num = copy.get(0);
                score.multiplyWith(tripletScoring.get (num));
                results.removeIf (y -> y.equals (num));
            }
            else if (copy.size() == 4)
            {
                Debug ("quadruple");
                var num = copy.get(0);
                score.multiplyWith(quadScoring.get (num));
                results.removeIf (y -> y.equals (num));
            }

            // always make n positive
            if (copy.size() > 0) {
                if (copy.get(0).equals("O")) {
                    Score.modScore(score);
                }
            }
        }
    }

    // calculate how many extra dice the player gets after rolling
    public int calculateDice (List <String> results)
    {
        return getAllWords(results).size();
    }

    // get a list of all possible words from a list of results
    public List<String> getAllWords (List <String> results)
    {
        List<String> output = new ArrayList<String>();

        // get powerset
        List <List <String>> ps = findPowerset(results);

        // find all words in results regardless of order
        for (List <String> set : ps) {

            // combines input lists into actual strings
            StringBuilder word = new StringBuilder();
            for (String s : set) {
                word.append(s);
            }

            // logs found words
            for (String w : wordsScoring.keySet()) {
                if (doubleInclusion(word.toString(), w)) {
                    output.add (w);
                }
            }
        }

        return output;
    }

    // test if two strings are subsets of each other
    public boolean doubleInclusion (String s1, String s2)
    {
        return isSubset(s1, s2) && isSubset(s2, s1);
    }

    // test if string a is a subset of string b
    public boolean isSubset (String a_, String b_)
    {
        List<Character> a = convertStringToCharList(a_);
        List<Character> b = convertStringToCharList(b_);

        for (Character c : a)
        {
            if (!b.contains(c))
                return false;
        }

        return true;
    }

    // convert string to a list of Character
    public static List<Character> convertStringToCharList(String str)
    {
        List<Character> chars = new ArrayList<>();

        for (char ch : str.toCharArray()) {
            chars.add(ch);
        }

        // return the List
        return chars;
    }

    // find the powerset of a list of strings
    public List <List <String>> findPowerset (List <String> set)
    {
        int pslen = (int) Math.pow (2, set.size());
        List <List <String>> powerset = new ArrayList<List<String>>();

        for (int c = 0; c < pslen; c++)
        {
            List <String> subset = new ArrayList<String>();
            for (int i = 0; i < set.size(); i++)
            {
                if((c & (1 << i)) > 0){
                    subset.add (set.get(i));
                }
            }
            powerset.add(subset);
        }

        return powerset;
    }

    // screen to show before the player rolls
    public void screenPreRoll ()
    {
        System.out.println("Kodice");
        printInformation();

        // game loop
        if (numDice >= 0) {
            System.out.println("Enter new line to roll..");

            Scanner keyboard = new Scanner(System.in);
            keyboard.nextLine();
            Debug ("penis");
            roll();
        }
        else
        {
            screenGameOver();
        }
    }

    // screen to show when the player runs out of rolls
    public void screenGameOver()
    {
        System.out.println ("Game Over");
        printInformation();
        System.exit(0);
    }
}
