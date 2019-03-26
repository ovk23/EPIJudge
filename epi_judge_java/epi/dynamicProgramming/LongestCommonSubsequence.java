package epi.dynamicProgramming;

import java.util.LinkedHashSet;
import java.util.Set;

public class LongestCommonSubsequence {

    private static int lcs(String a, String b){
        char[] A = a.toCharArray();
        char[] B = b.toCharArray();
        int[][] temp = new int[A.length + 1][B.length + 1];
        int max = 0;

        for(int i = 1; i < temp.length; i++){
            for(int j = 1; j < temp[i].length; j++){
                temp[i][j] = A[i-1] == B[j-1]
                    ?   1 + temp[i-1][j-1]
                        :   Math.max(temp[i-1][j], temp[i][j-1]);

                max = Math.max(temp[i][j], max);
            }
        }

        return max;
    }

    public static void main(String[] args) {
        Set<String> test = new LinkedHashSet<>();
        test.add("test");
        test.add("test2");

        System.out.println(lcs("abcdaf", "acbcf"));
    }
}
