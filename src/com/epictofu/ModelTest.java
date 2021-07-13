package com.epictofu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void updateScore() {
    }

    @Test
    void updateRollsAndDice() {
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
    @DisplayName("test if one string is equal to another regardless of character order")
    void doubleInclusionTest() {
        assertTrue (Model.isSubset("penis", "pneis"));
    }

    @Test
    @DisplayName("test if one string is a subset of another")
    void isSubsetTest() {
        assertTrue (Model.isSubset("penis", "bigpenis"));
    }
}