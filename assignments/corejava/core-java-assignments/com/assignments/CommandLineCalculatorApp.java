package com.assignments;

public class CommandLineCalculatorApp {
    public static void main(String[] args) {
        if(args.length!=3){
            System.out.println("Error: The correct command line format was <input1> <operator> <input2>");
            return;
        }

        double input1= Double.parseDouble((args[0]));
        char operator=args[1].charAt(0);
        double input2= Double.parseDouble(args[2]);
        double result=0;

        switch (operator){
            case'+':
                result= input1+input2;
                break;
            case'-':
                result=input1-input2;
                break;
            case'*':
                result=input1*input2;
                break;
            case'/':
                if(input2!=0){
                result=input1/input2;
                }
                else {
                    System.out.println("Error: It cannot be divided by zero");
                }
                break;
        }
        System.out.println("Answer: "+result);
    }
}
