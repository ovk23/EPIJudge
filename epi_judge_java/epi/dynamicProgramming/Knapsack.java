package epi.dynamicProgramming;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;

import java.util.Arrays;
import java.util.List;

public class Knapsack {
    @EpiUserType(ctorParams = {Integer.class, Integer.class})

    public static class Item {
        public Integer weight;
        public Integer value;

        public Item(Integer weight, Integer value) {
            this.weight = weight;
            this.value = value;
        }
    }

    @EpiTest(testDataFile = "knapsack.tsv")

    public static int optimumSubjectToCapacity(List<Item> items, int capacity) {
        int[][] c = new int[items.size()][capacity + 1];

        for (int[] arr: c) {
            Arrays.fill(arr, -1);
        }

        return helper(items, items.size() - 1, capacity, c);
    }

    static int helper(List<Item> items, int k, int c, int[][] capacity) {
        if (k < 0) return 0;

        if (capacity[k][c] == -1) {
            int withItem = c < items.get(k).weight
                    ?   0
                    :   items.get(k).value +
                        helper(items,
                                k - 1,
                                c - items.get(k).weight,
                                capacity);

            int withoutItem = helper(items, k - 1, c, capacity);

            capacity[k][c] = Math.max(withItem, withoutItem);
        }

        return capacity[k][c];
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "Knapsack.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
