package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpressionTreeTest {

    @Test
    public void testSingleNumber() {
        var calculator = new ExpressionTree();

        var tree = calculator.buildTree("100");
        var result = calculator.calculate(tree);

        assertEquals(100, result);
    }

    @Test
    public void testAddingTwoNumbers() {
        var calculator = new ExpressionTree();

        var tree = calculator.buildTree("100 + 11");
        var result = calculator.calculate(tree);

        assertEquals(111, result);
    }

    @Test
    public void testAddingThreeNumbers() {
        var calculator = new ExpressionTree();

        var tree = calculator.buildTree("100 + 11 + 100");
        var result = calculator.calculate(tree);

        assertEquals(211, result);
    }

    @Test
    public void testWithParenthesis() {
        var calculator = new ExpressionTree();

        var tree = calculator.buildTree("(1 + 1) * 2");
        var result = calculator.calculate(tree);

        assertEquals(4, result);
    }

    @Test
    public void testComplexArithmetic() {
        var calculator = new ExpressionTree();

        var tree2 = calculator.buildTree("((15/(7-(1+1)))*-3)-(2+(1+1))");
        var result2 = calculator.calculate(tree2);

        assertEquals(-13, result2);
    }

}
