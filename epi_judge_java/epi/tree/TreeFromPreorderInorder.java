package epi.tree;

import epi.BinaryTree;
import epi.BinaryTreeNode;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TreeFromPreorderInorder {
    @EpiTest(testDataFile = "tree_from_preorder_inorder.tsv")

    public static BinaryTreeNode<Integer>
    binaryTreeFromPreorderInorder(List<Integer> preorder, List<Integer> inorder) {
        return helper(preorder, 0, preorder.size(), 0, inorder.size(),
                IntStream.range(0, inorder.size()).boxed().collect(Collectors.toMap(inorder::get, i -> i))
        );
    }

    public static BinaryTreeNode<Integer> helper(List<Integer> preorder, int preorderStart, int preorderEnd,
                                                 int inorderstart, int inorderEnd, Map<Integer, Integer> nodeToInorderIdx) {
        if (preorderStart >= preorderEnd || inorderstart >= preorderEnd) {
            return null;
        }

        int inorderIdx = nodeToInorderIdx.get(preorder.get(preorderStart));
        int leftSubTreeSize = inorderIdx - inorderstart;

        BinaryTreeNode<Integer> leftTree = helper(preorder, preorderStart + 1, preorderStart + 1 + leftSubTreeSize,
                inorderstart, inorderIdx, nodeToInorderIdx);

        BinaryTreeNode<Integer> rightTree = helper(preorder, preorderStart + leftSubTreeSize + 1, preorderEnd,
                inorderIdx + 1, inorderEnd, nodeToInorderIdx);

        return new BinaryTreeNode<>(
                preorder.get(preorderStart),
                leftTree,
                rightTree
        );
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "TreeFromPreorderInorder.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
