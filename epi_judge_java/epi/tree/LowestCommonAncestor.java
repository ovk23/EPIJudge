package epi.tree;

import epi.BinaryTreeNode;
import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

public class LowestCommonAncestor {
    public static class Status {
        public BinaryTreeNode<Integer> ancestor;
        public int numOfTargetNodes;

        public Status(int numOfTargetNodes, BinaryTreeNode<Integer> ancestor) {
            this.ancestor = ancestor;
            this.numOfTargetNodes = numOfTargetNodes;
        }
    }

    public static BinaryTreeNode<Integer> LCA(BinaryTreeNode<Integer> tree,
                                              BinaryTreeNode<Integer> node0,
                                              BinaryTreeNode<Integer> node1) {
        return helper(tree, node0, node1).ancestor;
    }

    public static Status helper(BinaryTreeNode<Integer> tree,
                                BinaryTreeNode<Integer> node0,
                                BinaryTreeNode<Integer> node1) {
        if (tree == null) return new Status(0, null);

        Status left = helper(tree.left, node0, node1);

        if (left.numOfTargetNodes == 2) return left;

        Status right = helper(tree.right, node0, node1);

        if (right.numOfTargetNodes == 2) return right;

        int numOfNodes = left.numOfTargetNodes + right.numOfTargetNodes
                + (tree == node0 ? 1 : 0) + (tree == node1 ? 1 : 0);

        BinaryTreeNode<Integer> ancestor = numOfNodes == 2 ? tree : null;

        return new Status(numOfNodes, ancestor);
    }

    @EpiTest(testDataFile = "lowest_common_ancestor.tsv")
    public static int lcaWrapper(TimedExecutor executor,
                                 BinaryTreeNode<Integer> tree, Integer key0,
                                 Integer key1) throws Exception {
        BinaryTreeNode<Integer> node0 = BinaryTreeUtils.mustFindNode(tree, key0);
        BinaryTreeNode<Integer> node1 = BinaryTreeUtils.mustFindNode(tree, key1);

        BinaryTreeNode<Integer> result =
                executor.run(() -> LCA(tree, node0, node1));

        if (result == null) {
            throw new TestFailure("Result can not be null");
        }
        return result.data;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "LowestCommonAncestor.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
