package epi.tree;

import epi.BinaryTree;
import epi.BinaryTreeNode;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.List;

public class TreeFromPreorderWithNull {
    public static int idx;

    public static BinaryTreeNode<Integer> reconstructPreorder(List<Integer> preorder) {
        idx = 0;
        return helper(preorder);
    }

    static BinaryTreeNode<Integer> helper(List<Integer> preorder) {
        Integer treeKey = preorder.get(idx);
        idx++;
        if (treeKey == null) return null;

        BinaryTreeNode<Integer> left = helper(preorder);
        BinaryTreeNode<Integer> right = helper(preorder);

        return new BinaryTreeNode<>(treeKey, left, right);
    }

    @EpiTest(testDataFile = "tree_from_preorder_with_null.tsv")
    public static BinaryTreeNode<Integer>
    reconstructPreorderWrapper(TimedExecutor executor, List<String> strings)
            throws Exception {
        List<Integer> ints = new ArrayList<>();
        for (String s : strings) {
            if (s.equals("null")) {
                ints.add(null);
            } else {
                ints.add(Integer.parseInt(s));
            }
        }

        return executor.run(() -> reconstructPreorder(ints));
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "TreeFromPreorderWithNull.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
