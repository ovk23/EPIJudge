package epi.tree;
import epi.BinaryTreeNode;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsTreeBalanced {

  public static class BalancedStatusWithHeight {
    public boolean balanced;
    public int height;

    public BalancedStatusWithHeight (int height, boolean balanced) {
      this.balanced = balanced;
      this.height = height;
    }
  }

  @EpiTest(testDataFile = "is_tree_balanced.tsv")
  public static boolean isBalanced(BinaryTreeNode<Integer> tree) {
    return checkHeight(tree).balanced;
  }

  public static BalancedStatusWithHeight checkHeight(BinaryTreeNode<Integer> tree) {
    if (tree == null) return new BalancedStatusWithHeight(-1, true);

    BalancedStatusWithHeight left = checkHeight(tree.left);

    if (!left.balanced) return left;

    BalancedStatusWithHeight right = checkHeight(tree.right);

    if(!right.balanced) return right;
    int height = Math.max(left.height, right.height) + 1;
    boolean balanced = Math.abs(left.height - right.height) <= 1;

    return new BalancedStatusWithHeight(height, balanced);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeBalanced.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
