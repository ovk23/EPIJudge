package epi.string;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class SubstringMatch {
  @EpiTest(testDataFile = "substring_match.tsv")

  /**
   * Rolling hash Function:
   *
   * thash = thash - [t.charAt(i - s.length()) * (BASE ^ m)] ... where m = s.length()
   * thash = thash * BASE + t.charAt(i)
   */
  public static int rabinKarp(String t, String s) {
    if(s.length() > t.length()) return -1;

    final int BASE = 26;
    int thash = 0, shash = 0;
    int powerS = -558124416;

    int m = s.length() - 1;
//    int k =  m < 0 ? (int)Math.pow(BASE, 0) : (int)Math.pow(BASE, m);
    for(int i = 0; i < s.length(); i++){
//      powerS = i > 0 ? powerS * BASE : 1; // BASE ^ i
      thash = thash * BASE + t.charAt(i);
      shash = shash * BASE + s.charAt(i);
    }

    System.out.println(powerS);
    for(int i = s.length(); i < t.length(); i++){
      if(thash == shash && t.substring(i - s.length(), i).equals(s))
        return i - s.length();

      thash -= (t.charAt(i - s.length()) * powerS);
      thash = thash * BASE + t.charAt(i);
    }

    if(thash == shash && t.substring(t.length() - s.length(), t.length()).equals(s))
      return t.length() - s.length();

    return -1;
  }

  public static void main(String[] args) {
      System.out.println(rabinKarp("abbaabbbabaaaabbabbaabbaaaaabaaabbaaaabbbbbabbbbaabbbabaaaaaaaaaaaaaabbaabababaabbbbbbbbbbbbbbbbaba", "aabbabba"));
//    System.exit(
//        GenericTest
//            .runFromAnnotations(args, "SubstringMatch.java",
//                                new Object() {}.getClass().getEnclosingClass())
//            .ordinal());
  }
}
