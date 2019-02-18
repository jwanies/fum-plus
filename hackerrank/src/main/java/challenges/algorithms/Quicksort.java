package challenges.algorithms;

import java.util.Arrays;

public class Quicksort {

	public static void main(String[] args) {
		
		int[] listToSort = {4,2,7,234,32,1,6,34};
		qs(listToSort, 0, listToSort.length-1);
		System.out.println(Arrays.toString(listToSort));
		
	}
	
	public static void qs(int arr[], int begin, int end) {
	    
		if (begin < end) {	
	        int partitionIndex = partition(arr, begin, end);
	        qs(arr, begin, partitionIndex-1);
	        qs(arr, partitionIndex+1, end);
	    }
		
	}
	
	private static int partition(int arr[], int begin, int end) {
		
	    int pivot = arr[end];
	    int i = begin-1; // keeps track of index of last integer smaller than pivot
	 
	    for (int j = begin; j < end; j++) {
	        if (arr[j] <= pivot) {
	            i++; // update index of last integer that was smaller than pivot
	            int swapTemp = arr[i];
	            arr[i] = arr[j];
	            arr[j] = swapTemp;
	        }
	    }
	 
	    int swapTemp = arr[i+1];
	    arr[i+1] = arr[end];
	    arr[end] = swapTemp;
	 
	    return i+1;
	}
	
}
