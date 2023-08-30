package org.example;

public class LabTask {
    public static void main(String[] args) {

       if(args.length==0){
           System.out.println("No arguments are passed");
           return;
       }
        System.out.println("The length of the first argument: " + args[0].length());
        System.out.println("The conversion of the second argument to lower case: " + args[1].toLowerCase());
//        StringBuilder reversed = new StringBuilder(args[2]);
//        reversed.reverse();

       String arrArgument3 = args[2];
        String reversed = "";
        for (int i = arrArgument3.length()-1; i >= 0; i--) {
            reversed += arrArgument3.charAt(i);
        }
        System.out.println("The reversal of third argument: " + reversed);
        System.out.println("The substring of the fifth argument: "+ args[4].substring(4,11));
//        System.out.println("splitting of sixth argument: "+args[5]);
        String[] arrArgument6 = args[5].split(",");

        // Print each element of the array
        for (String temp : arrArgument6) {
            System.out.println("splitting of sixth argument into tokens: " + temp);
//       for(String arg: args){
//           System.out.println(arg);
        }
    }
}