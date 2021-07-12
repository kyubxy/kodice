package com.epictofu;

import java.util.*;


public class Kodice {
    Score score = Score.ZeroScore;
    int numDice = 5;
    int rolls = 3;

    String[] dice = {"U", "CHI", "N", "O", "MA", "KO"};

    Map <String, Score> singleDiceScoring = new HashMap<String, Score>();
    Map <String, Score> wordsScoring = new HashMap<String, Score>();

    Random rand;

    public Kodice ()
    {
        init();
        //screenPreRoll();
        List <String> wh = new ArrayList<>();
        wh.add("U");
        wh.add("N");
        wh.add("MA");
        wh.add("KO");
        System.out.println (calculateDice(wh));
    }

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
        wordsScoring.put ("OCHINCHIN", new Score (10000));
    }

    public void printInformation()
    {
        System.out.println("Current score: " + Integer.toString (score.getTotal()));
        System.out.println("U: " + Integer.toString (score.u));
        System.out.println("M: " + Integer.toString (score.m));
        System.out.println("C: " + Integer.toString (score.c));
        System.out.println("Rolls left: " + Integer.toString (rolls));
        System.out.println("Dice left: " + Integer.toString (numDice));
    }

    public void roll ()
    {
        List <String> results = new ArrayList<String>();

        // TODO: add piss

        for (int i = 0; i < rolls; i++)
        {
            var result = dice[rand.nextInt(dice.length)];
            results.add(result);
        }

        score.addWith(calculateScore(results));

        int dice = calculateDice (results);
        numDice += dice;
        rolls += dice;
    }

    public Score calculateScore (List <String> results)
    {
        Score scoreacc = Score.ZeroScore;

        // dice faces
        for (String working: results)
        {
            for (String )
        }

        return scoreacc;
    }

    public int calculateDice (List <String> results)
    {
        int turns = 0;

        // get powerset
        List <List <String>> ps = findPowerset(results);

        for (List <String> set : ps)
        {
            // combines lists of strings
            StringBuilder word = new StringBuilder();
            for (String s : set)
            {
                word.append(s);
            }

            for (String w : wordsScoring.keySet())
            {
                if (doubleInclusion(word.toString(), w)) {
                    turns++;
                }
            }
        }

        return turns;
    }


    public boolean doubleInclusion (String s1, String s2)
    {
        return isSubset(s1, s2) && isSubset(s2, s1);
    }
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

    public static List<Character> convertStringToCharList(String str)
    {
        List<Character> chars = new ArrayList<>();

        for (char ch : str.toCharArray()) {
            chars.add(ch);
        }

        // return the List
        return chars;
    }

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

    public void screenPreRoll ()
    {
        System.out.println("Kodice");
        printInformation();

        // game loop
        if (numDice >= 0) {
            System.out.println("Enter new line to roll..");

            Scanner keyboard = new Scanner(System.in);
            keyboard.nextLine();
            roll();
        }
        else
        {
            screenGameOver();
        }
    }

    public void screenGameOver()
    {
        System.out.println ("Game Over");
        printInformation();
        System.exit(0);
    }
}
