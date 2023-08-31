package com.assignments;

public class EnhancedCalculator {
    public static double add(double a, double b) {
        return a + b;
    }

    public static double subtract(double a, double b) {
        return a - b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    public static double divide(double a, double b) {
        if (b != 0) {
            return a / b;
        } else {
            throw new ArithmeticException("Cannot divide by zero");
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Error: The correct command line format was <input1> <operator> <input2>");
            return;
        }

        double operand1 = Double.parseDouble(args[0]);
        double operand2 = Double.parseDouble(args[1]);
        String operation = args[2].toLowerCase();

        double result = 0;

        switch (operation) {
            case "add":
                result = add(operand1, operand2);
                break;
            case "sub":
                result = subtract(operand1, operand2);
                break;
            case "mul":
                result = multiply(operand1, operand2);
                break;
            case "div":
                result = divide(operand1, operand2);
                break;
            default:
                System.out.println("Invalid operation type");
                return;
        }

        System.out.println("Result: " + result);
    }
}

