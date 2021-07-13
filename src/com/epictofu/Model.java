package com.epictofu;

import java.util.*;

// Handles game specific states and methods of processing those states
public class Model {
    public Score score = new Score(0);
    public int numDice = 5;
    public int rolls = 3;

    String[] dice = {"U", "CHI", "N", "O", "MA", "KO"};

    Map <String, Score> singleDiceScoring = new HashMap<String, Score>();
    Map <String, Score> wordsScoring = new HashMap<String, Score>();
    Map <String, Score> tripletScoring = new HashMap<String, Score>();
    Map <String, Score> quadScoring = new HashMap<String, Score>();

    Random rand;

    public Model ()
    {
        // initialise data

        rand = new Random();

        singleDiceScoring.put ("U", new Score (500,0,0));
        singleDiceScoring.put ("MA", new Score (0, 500,0));
        singleDiceScoring.put ("CHI", new Score(0,0,500));
        singleDiceScoring.put ("N", new Score(50));
        singleDiceScoring.put ("KO", new Score (100));
        singleDiceScoring.put ("O", new Score (300));

        wordsScoring.put ("UNCHI",new Score (1000, 0,  0));
        wordsScoring.put ("UNKO", new Score (1000, 0,  0));
        wordsScoring.put ("MANKO", new Score (0, 1000, 0));
        wordsScoring.put ("OMANKO",new Score (0, 4000, 0));
        wordsScoring.put ("CHINKO", new Score (0,0,1000));
        wordsScoring.put ("CHINCHIN", new Score (3000));
        wordsScoring.put ("OCHINCHIN", new Score (7000));

        tripletScoring.put ("U", new Score (2,1,1));
        tripletScoring.put ("MA", new Score (1, 2,1));
        tripletScoring.put ("CHI", new Score(1,1,2));
        tripletScoring.put ("N", new Score(-3));
        tripletScoring.put ("KO", new Score (1.5));
        tripletScoring.put ("O", new Score (1.5));

        quadScoring.put ("U", new Score (4,1,1));
        quadScoring.put ("MA", new Score (1, 4,1));
        quadScoring.put ("CHI", new Score(1,1,4));
        quadScoring.put ("N", new Score(-4));
        quadScoring.put ("KO", new Score (4));
        quadScoring.put ("O", new Score (4));
    }

    // simulate a roll
    public List<String> roll ()
    {
        List<String> results = new ArrayList<String>();

        for (int i = 0; i < numDice; i++)
        {
            var result = dice[rand.nextInt(dice.length)];
            results.add(result);
        }
        // TODO: add piss

        return results;

    }
    // update the score given results according to the scoring table
    public void updateScore (List <String> results)
    {
        Score scoreacc = new Score(0);

        // dice faces
        for (String working : results)
        {
            scoreacc.addWith (singleDiceScoring.get (working));
        }

        // words
        List <String> words = getAllWords(results);

        for (String word : words)
        {
            scoreacc.addWith (wordsScoring.get(word));
            System.out.println (word);
        }

        score.addWith(scoreacc);
    }

    // update how many extra dice the player gets after rolling
    public void updateRollsAndDice (List <String> results)
    {
        var words = getAllWords(results);
        if (words.size() > 0) {
            if (words.contains ("OCHINCHIN"))
                numDice = 10;
            else {
                if (numDice < 10)
                    numDice++;
            }
        }
        else {
            rolls--;
            if (numDice > 5)
                numDice --;
        }
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
            copy.removeIf (y -> !y.equals(x));

            if (copy.size() == 3)
            {
                var num = copy.get(0);
                score.multiplyWith(tripletScoring.get (num));
                results.removeIf (y -> y.equals (num));
                System.out.println (num + " x3");
            }
            else if (copy.size() >= 4)
            {
                var num = copy.get(0);
                score.multiplyWith(quadScoring.get (num));
                results.removeIf (y -> y.equals (num));
                if (copy.size() == 4)
                    System.out.println (num + " x4");
                else
                    System.out.println (num + " x5");
            }

            // always make n positive
            if (copy.size() > 0) {
                if (copy.get(0).equals("O")) {
                    score.modScore();
                }
            }
        }
    }

    // vv helper functions

    // get a list of all possible words from a list of results
    List<String> getAllWords (List <String> results)
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

    // test if two strings are subsets of each other and if they are of same length
    // XXX: not really a double inclusion in the mathematical sense
    static boolean doubleInclusion (String s1, String s2)
    {
        return isSubset(s1, s2) && isSubset(s2, s1) && s1.length() == s2.length();
    }

    // test if string a is a subset of string b
    static boolean isSubset (String a_, String b_)
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
    static List<Character> convertStringToCharList(String str)
    {
        List<Character> chars = new ArrayList<>();

        for (char ch : str.toCharArray()) {
            chars.add(ch);
        }

        // return the List
        return chars;
    }

    // find the powerset of a list of strings
    static List <List <String>> findPowerset (List <String> set)
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
}
