package com.epictofu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {


    @Test
    @DisplayName ("roll and update rolls and dice")
    void updateRollsAndDiceTest() {
        Model m = new Model();
        List<String> results = new ArrayList<String>();
        results.add("MA");
        results.add("KO");
        m.updateRollsAndDice(results);

        assertTrue(m.rolls == 2 && m.numDice == 5);
    }

    @Test
    @DisplayName ("roll no words with 6 die and update rolls and dice")
    void updateRollsAndDiceGoodButNoWordTest() {
        Model m = new Model();
        m.numDice = 6;

        List<String> results = new ArrayList<String>();
        results.add("MA");
        results.add("KO");
        m.updateRollsAndDice(results);

        assertTrue(m.rolls == 2 && m.numDice == 5);
    }

    @Test
    @DisplayName ("roll words with max die and update rolls and dice")
    void updateRollsAndDiceGoodWithWordTest() {
        Model m = new Model();
        m.numDice = 10;

        List<String> results = new ArrayList<String>();
        results.add("MA");
        results.add("N");
        results.add("KO");
        m.updateRollsAndDice(results);

        assertEquals(m.numDice,  10);
    }

    @Test
    @DisplayName ("roll word and update rolls and dice")
    void updateRollsAndDiceChinchinTest() {
        Model m = new Model();

        List<String> results = new ArrayList<String>();
        results.add("CHI");
        results.add("N");
        results.add("CHI");
        results.add("N");
        m.updateRollsAndDice(results);

        assertEquals(m.numDice,  6);
    }
    @Test
    @DisplayName ("roll OCHINCHIN and update rolls and dice")
    void updateRollsAndDiceOchinchinTest() {
        Model m = new Model();

        List<String> results = new ArrayList<String>();
        results.add("O");
        results.add("CHI");
        results.add("N");
        results.add("CHI");
        results.add("N");
        m.updateRollsAndDice(results);

        assertEquals(m.numDice,  10);
    }

    @Test
    @DisplayName ("roll MANKO and update rolls and dice")
    void updateRollsAndDiceMankoTest() {
        Model m = new Model();
        List<String> results = new ArrayList<String>();
        results.add("MA");
        results.add("N");
        results.add("KO");
        m.updateRollsAndDice(results);

        assertTrue(m.rolls == 3 && m.numDice == 6);
    }

    @Test
    @DisplayName ("roll MANKO")
    void updateScoreMankoTest() {
        Model m = new Model();
        m.score = new Score(0);

        List<String> results = new ArrayList<String>();
        results.add("MA");
        results.add("N");
        results.add("KO");
        m.updateScore(results);
        m.applyMultipliers(results);

        assertEquals(new Score(50+100,500+50+100+1000,50+100), m.score);
    }

    @Test
    @DisplayName ("roll OCHINCHIN")
    void updateScoreOchinchinTest() {
        Model m = new Model();
        m.score = new Score(0);

        List<String> results = new ArrayList<String>();
        results.add("O");
        results.add("CHI");
        results.add("N");
        results.add("CHI");
        results.add("N");
        m.updateScore(results);
        m.applyMultipliers(results);

        assertEquals(new Score(100+10000+300,100+10000+300,100+500+500+10000+300), m.score);
    }

    @Test
    @DisplayName ("roll CHINCHIN")
    void updateScoreChinchinTest() {
        Model m = new Model();
        m.score = new Score(0);

        List<String> results = new ArrayList<String>();
        results.add("CHI");
        results.add("N");
        results.add("CHI");
        results.add("N");
        m.updateScore(results);
        m.applyMultipliers(results);

        assertEquals(new Score(100+3000,100+3000,100+500+500+3000), m.score);
    }

    @Test
    @DisplayName ("roll OMANKO")
    void updateScoreOmankoTest() {
        Model m = new Model();
        m.score = new Score(0);

        List<String> results = new ArrayList<String>();
        results.add("O");
        results.add("MA");
        results.add("N");
        results.add("KO");
        m.updateScore(results);
        m.applyMultipliers(results);

        assertEquals(new Score(300+50+100,300+500+50+100+5000,300+50+100), m.score);
    }

    @Test
    @DisplayName ("roll CHINKO")
    void updateScoreChinkoTest() {
        Model m = new Model();
        m.score = new Score(0);

        List<String> results = new ArrayList<String>();
        results.add("CHI");
        results.add("N");
        results.add("KO");
        m.updateScore(results);
        m.applyMultipliers(results);

        assertEquals(new Score(50+100,50+100,500+50+100+1000), m.score);
    }

    @Test
    @DisplayName ("roll UNCHI")
    void updateScoreUnchiTest() {
        Model m = new Model();
        m.score = new Score(0);

        List<String> results = new ArrayList<String>();
        results.add ("U");
        results.add ("N");
        results.add ("CHI");
        m.updateScore(results);
        m.applyMultipliers(results);

        assertEquals(new Score(500+50+1000, 50, 50+500), m.score);
    }


    @Test
    @DisplayName("roll 3 U")
    void applyMultipliersUTest() {
        Model m = new Model();
        m.score = new Score(1,1,1);

        List<String> results = new ArrayList<String>();
        results.add ("U");
        results.add ("U");
        results.add ("U");
        m.applyMultipliers(results);

        assertEquals(new Score(2, 1, 1), m.score);
    }

    @Test
    @DisplayName("roll 3 chi")
    void applyMultipliersChiTest() {
        Model m = new Model();
        m.score = new Score(1,1,1);

        List<String> results = new ArrayList<String>();
        results.add ("CHI");
        results.add ("CHI");
        results.add ("CHI");
        m.applyMultipliers(results);

        assertEquals(new Score(1, 1, 2), m.score);
    }

    @Test
    @DisplayName("roll 3 n")
    void applyMultipliersNTest() {
        Model m = new Model();
        m.score = new Score(1,1,1);

        List<String> results = new ArrayList<String>();
        results.add ("N");
        results.add ("N");
        results.add ("N");
        m.applyMultipliers(results);

        assertEquals(new Score(-3, -3, -3), m.score);
    }

    @Test
    @DisplayName("roll 3 o with positive score")
    void applyMultipliersOPosTest() {
        Model m = new Model();
        m.score = new Score(1,1,1);

        List<String> results = new ArrayList<String>();
        results.add ("O");
        results.add ("O");
        results.add ("O");
        m.applyMultipliers(results);

        assertEquals(new Score(1.5, 1.5, 1.5), m.score);
    }

    @Test
    @DisplayName("roll 3 o with negative score")
    void applyMultipliersONegTest() {
        Model m = new Model();
        m.score = new Score(-1,-1,-1);

        List<String> results = new ArrayList<String>();
        results.add ("O");
        results.add ("O");
        results.add ("O");
        m.applyMultipliers(results);

        assertEquals(new Score(1.5, 1.5, 1.5), m.score);
    }

    @Test
    @DisplayName("test if one string doubled is not equal to another regardless of character order")
    void doubleInclusionTestDouble() {
        assertFalse (Model.doubleInclusion("penispenis", "pneis"));
    }

    @Test
    @DisplayName("test if one string is equal to another regardless of character order")
    void doubleInclusionTest() {
        assertTrue (Model.doubleInclusion("penis", "pneis"));
    }

    @Test
    @DisplayName("test if one string is a subset of another")
    void isSubsetTest() {
        assertTrue (Model.isSubset("penis", "bigpenis"));
    }
}