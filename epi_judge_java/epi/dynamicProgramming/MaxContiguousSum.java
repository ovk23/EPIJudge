package epi.dynamicProgramming;

public class MaxContiguousSum {
    public static void main(String[] args) {
        int[] arr = {1, -3, 4, -2, -1, 6};
        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            maxSum = Math.max(sum, maxSum);
            for (int j = i; j < arr.length; j++) {
                sum += arr[j];
                maxSum = Math.max(maxSum, sum);
            }
        }
        System.out.println(maxSum);
    }
}
