package org.example;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static java.util.Objects.isNull;

public class ExpressionTree {

    public static void main(String[] args) {

        if (isNull(args) || args.length == 0) {
            throw new IllegalArgumentException("Invalid arguments!");
        }

        var calculator = new ExpressionTree();
        var node = calculator.buildTree(args[0]);

    }


    private final Map<Character, Integer> OPS_PRIORITY = Map.of(
            '(', 1,
            '+', 2,
            '-', 2,
            '*', 3,
            '/', 3
    );

    public static class Node {
        Character value;
        Node left;
        Node right;

        public Node(Character value) {
            this.value = value;
        }
    }

    public Node buildTree(String expression) {

        var ops = new ArrayDeque<Character>();
        var stack = new ArrayDeque<Node>();

        for (int i = 0; i < expression.length(); i++) {
            var character = expression.charAt(i);
            if (character == '(') {
                ops.push(character);
            } else if (Character.isDigit(character)) {
                stack.push(new Node(character));
            } else if (character == ')') {
                while (ops.peek() != '(') {
                    combine(ops, stack);
                }
                ops.pop();
            } else {
                while (!ops.isEmpty() && OPS_PRIORITY.get(ops.peek()) >= OPS_PRIORITY.get(character)) {
                    combine(ops, stack);
                }
                ops.push(character);
            }
        }

        while (stack.size() > 1) {
            combine(ops, stack);
        }
        return stack.peek();
    }

    private void combine(Deque<Character> ops, Deque<Node> stack) {
        Node root = new Node(ops.pop());
        root.right = stack.pop();
        root.left = stack.pop();

        stack.push(root);
    }
}