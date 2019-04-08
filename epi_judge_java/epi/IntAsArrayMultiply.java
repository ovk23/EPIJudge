package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IntAsArrayMultiply {
    @EpiTest(testDataFile = "int_as_array_multiply.tsv")
    public static List<Integer> multiply(List<Integer> num1, List<Integer> num2) {
        // TODO - you fill in here.
        System.out.println((int) Math.pow(10, 2));
        return null;
    }

    public static void main(String[] args) {
        String a = "1234";
        List<Integer> preOrder = Arrays.asList(a.split(""))
                .stream().filter(i -> !i.equals("")).map(Integer::parseInt).collect(Collectors.toList());
        System.out.println(preOrder.toString());
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "IntAsArrayMultiply.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
