import java.util.Scanner;

public class CalculatorApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the 1st number: ");
        int input1 = scanner.nextInt();
//        System.out.println("You entered: " + input1);

        System.out.print("Enter an operator (+, -, *, /): ");
        String operator = scanner.next();
//        System.out.println("You entered: " + operator);

        System.out.print("Enter the 2nd number: ");
        int input2 = scanner.nextInt();
//        System.out.println("You entered: " + input2);

        switch (operator) {
            case "+" -> {
                int result = input1 + input2;
                System.out.println("Answer: "+result);
            }
            case "-" -> {
                int result = input1 - input2;
                System.out.println("Answer: "+result);
            }
            case "*" -> {
                int result = input1 * input2;
                System.out.println("Answer: "+result);
            }
            case "/" -> {
                if(input2 !=0 ){
                float result = ((float) input1 / input2);
                System.out.println("Answer: "+result);
                }else {
                    System.out.println("Error: cannot divide by zero");
                }

            }
            default -> System.out.println("Enter a valid operator from the list: Addition(+) Subtraction(-) Multiplication(*) Division(/)");
        }
        scanner.close();
    }
}
