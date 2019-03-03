package challenges.algorithms;
import java.util.Arrays;

public class Quicksort {

	public static void main(String[] args) {
		
		int[] listToSort = {4,2,7,234,32,1,6,34};
		qs(listToSort, 0, listToSort.length-1);
		System.out.println(Arrays.toString(listToSort));
		
	}

	private static void qs(int[] arr, int begin, int end) {
				
		if (arr == null || arr.length == 0)
			return;
					
		int pivot = arr[begin + (end - begin) / 2];
		int left = begin, right = end;
		
		while (left <= right) {
			
			while (arr[left] < pivot)
				left++;
			
			while (arr[right] > pivot)
				right--;
			
			if (left <= right) {
				swap(arr, left, right);
				left++;
				right--;
			}
		}
		
		if (left < end)
			qs(arr, left, end);
		if (right > begin)
			qs(arr, begin, right);
	}
	
	private static void swap(int[] arr, int begin, int end) {
		int temp = arr[begin];
		arr[begin] = arr[end];
		arr[end] = temp;
	}
	
}
