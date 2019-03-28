package epi.string;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

public class StringIntegerInterconversion {

  public static String intToString(int x) {
    boolean isNeg = false;

    if(x < 0) isNeg = true;

    StringBuilder s = new StringBuilder();

    while(x != 0){
      s.append((char)( '0' + Math.abs(x % 10)));
      x /= 10;
    }

    return s.append(isNeg ? '-' : "").reverse().toString();
  }

  public static int stringToInt(String s) {
    return (s.charAt(0) == '-' ? -1 : 1) *
            s.substring(s.charAt(0) == '-' ? 1 : 0)
              .chars()
              .reduce(0, (runningSum, c) -> runningSum * 10 + c - '0');
  }

  @EpiTest(testDataFile = "string_integer_interconversion.tsv")
  public static void wrapper(int x, String s) throws TestFailure {
    if (!intToString(x).equals(s)) {
      throw new TestFailure("Int to string conversion failed");
    }
    if (stringToInt(s) != x) {
      throw new TestFailure("String to int conversion failed");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "StringIntegerInterconversion.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
