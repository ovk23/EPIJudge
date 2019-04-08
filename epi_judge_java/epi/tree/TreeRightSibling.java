package epi.tree;

import epi.BinaryTree;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TreeRightSibling {
    public static class BinaryTreeNode<T> {
        public T data;
        public BinaryTreeNode<T> left, right;
        public BinaryTreeNode<T> next = null; // Populates this field.

        public BinaryTreeNode(T data) {
            this.data = data;
        }
    }

    private static void constructRightSibling(BinaryTreeNode<Integer> tree) {
        while (tree != null) {
            helper(tree);
            tree = tree.left;
        }
    }

    private static void helper(BinaryTreeNode<Integer> tree) {
        String abc = "1,2,3";
        List<Integer> ch = Arrays.stream(abc.split(",")).map(Integer::parseInt).collect(toList());
        while (tree != null && tree.left != null) {
            tree.left.next = tree.right;

            if (tree.next != null) {
                tree.right.next = tree.next.left;
            }
            tree = tree.next;
        }
    }

    private static BinaryTreeNode<Integer>
    cloneTree(BinaryTree<Integer> original) {
        if (original == null) {
            return null;
        }
        BinaryTreeNode<Integer> cloned = new BinaryTreeNode<>(original.data);
        cloned.left = cloneTree(original.left);
        cloned.right = cloneTree(original.right);
        return cloned;
    }

    @EpiTest(testDataFile = "tree_right_sibling.tsv")
    public static List<List<Integer>>
    constructRightSiblingWrapper(TimedExecutor executor, BinaryTree<Integer> tree)
            throws Exception {
        BinaryTreeNode<Integer> cloned = cloneTree(tree);

        executor.run(() -> constructRightSibling(cloned));

        List<List<Integer>> result = new ArrayList<>();
        BinaryTreeNode<Integer> levelStart = cloned;
        while (levelStart != null) {
            List<Integer> level = new ArrayList<>();
            BinaryTreeNode<Integer> levelIt = levelStart;
            while (levelIt != null) {
                level.add(levelIt.data);
                levelIt = levelIt.next;
            }
            result.add(level);
            levelStart = levelStart.left;
        }
        return result;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "TreeRightSibling.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
