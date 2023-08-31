package com.assignments;

import java.util.Arrays;
import java.util.Scanner;

public class BinarySearch {

    public static int binarySearchBySorting(int[] arr,int key){
        int left=0;
        int right=arr.length-1;

        while(left <= right){
            int midValue= left +(right-left)/2;

            if(arr[midValue]==key){
                return midValue;
            } else if (arr[midValue]<key) {
                left=midValue+1;
            }
            else{
                right=midValue-1;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the size of the array: ");
        int size= scanner.nextInt();
        int[] values = new int[size];

        System.out.println("Enter the array values: ");
        for(int i=0; i<size; i++){
            values[i]=scanner.nextInt();
        }

        System.out.println("Enter the key to search: ");
        int key = scanner.nextInt();
        scanner.close();

        Arrays.sort(values);
        int res = binarySearchBySorting(values,key);
        if(res!=1){
            System.out.println("key found at index "+ res);
        }
        else{
            System.out.println("key not found");
        }
    }
}
