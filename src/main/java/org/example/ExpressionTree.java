package org.example;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class ExpressionTree {

    public static void main(String[] args) {

        if (isNull(args) || args.length == 0) {
            throw new IllegalArgumentException("Invalid arguments!");
        }

        var calculator = new ExpressionTree();

        var input = String.join("", args);

        System.out.println("Input: " + input);

        var tree = calculator.buildTree(input);

        var result = calculator.calculate(tree);

        System.out.println("Output: " + result);

    }


    private final Map<Character, Integer> OPS_PRIORITY = Map.of(
            '(', 1,
            '+', 2,
            '-', 2,
            '*', 3,
            '/', 3
    );

    public static class Node {
        String value;
        Node left;
        Node right;

        public Node(String value) {
            this.value = value;
        }
    }

    public Node buildTree(String expression) {

        expression = expression.replaceAll(" ", "");

        var ops = new ArrayDeque<Character>();
        var stack = new ArrayDeque<Node>();

        var sign = 1;
        var n = expression.length();
        for (int i = 0; i < n; i++) {
            var character = expression.charAt(i);
            if (character == '(') {
                ops.push(character);
                sign = 1;
            } else if (Character.isDigit(character)) {

                int val = 0;
                while(i<n && Character.isDigit(expression.charAt(i))) {                     // if digit length > 1
                    val = val * 10 + (expression.charAt(i)-'0');
                    i++;
                }
                i--;
                val = sign * val;

                stack.push(new Node(val+""));
            } else if (character == ')') {
                while (ops.peek() != '(') {
                    combine(ops, stack);
                }
                ops.pop();
            } else {

                if (character == '-') {
                    sign = -1;
                } else {
                    sign = 1;
                }

                if (i > 0 && !OPS_EXCEPT_PARENTHESIS.contains(expression.charAt(i-1))) {           // skip minus in front of number
                    while (!ops.isEmpty() && OPS_PRIORITY.get(ops.peek()) >= OPS_PRIORITY.get(character)) {
                        combine(ops, stack);
                    }
                    ops.push(character);
                }

            }
        }

        while (stack.size() > 1) {
            combine(ops, stack);
        }
        return stack.peek();
    }

    private void combine(Deque<Character> ops, Deque<Node> stack) {
        var root = new Node(Character.toString(ops.pop()));
        root.right = stack.pop();
        root.left = stack.pop();

        stack.push(root);
    }

    public Integer calculate(Node node) {
        var val = node.value;

        if (isNumeric(val)) {
            return Integer.parseInt(val);
        }

        var left = calculate(node.left);
        var right = calculate(node.right);

        return func(val.charAt(0), left, right);
    }

    private Integer func(Character ops, Integer left, Integer right) {
        if (ops == '+') {
            return left + right;
        } else if (ops == '-') {
            return left - right;
        } else if (ops == '*') {
            return left * right;
        } else if (ops == '/') {
            return left/right;
        } else {
            throw new IllegalArgumentException("Invalid ops: " + ops);
        }
    }

    private boolean isNumeric(String value) {
        return value.matches("-?\\d+(\\.\\d+)?");
    }

    /*
    *
    * If minus sign comes after these signs, skip it and make the number next minus
    * */
    private final Set<Character> OPS_EXCEPT_PARENTHESIS = Set.of('+', '-', '*', '/');
}