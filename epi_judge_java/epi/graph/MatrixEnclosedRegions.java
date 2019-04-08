package epi.graph;

import epi.graph.SearchMaze;
import epi.graph.SearchMaze.Coordinate;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class MatrixEnclosedRegions {
    
    static void helper(int i, int j, List<List<Character>> board) {
        Queue<Coordinate> q = new ArrayDeque<>();
        
        q.add(new Coordinate(i, j));
        
        while (!q.isEmpty()) {
            Coordinate curr = q.poll();
            
            if (curr.x >= 0 &&
                curr.x <= board.size() &&
                curr.y >= 0 &&
                curr.y < board.get(curr.x).size() &&
                board.get(curr.x).get(curr.y) == 'W') {
                board.get(curr.x).set(curr.y, 'T');

                q.add(new Coordinate(curr.x, curr.y + 1));
                q.add(new Coordinate(curr.x + 1, curr.y));
                q.add(new Coordinate(curr.x - 1, curr.y));
                q.add(new Coordinate(curr.x, curr.y - 1));
            }
        }
    }

    public static void fillSurroundedRegions(List<List<Character>> board) {
        for (int i = 0; i < board.size(); i++) {
            helper(i, 0, board);
            helper(i, board.get(i).size(), board);
        }

        for (int j = 0; j < board.get(0).size(); j++) {
            helper(0, j, board);
            helper(board.size() - 1, j, board);
        }

        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                board.get(i).set(j, board.get(i).get(j) != 'T' ? 'B' : 'W');
            }
        }
    }

    @EpiTest(testDataFile = "matrix_enclosed_regions.tsv")
    public static List<List<Character>>
    fillSurroundedRegionsWrapper(List<List<Character>> board) {
        fillSurroundedRegions(board);
        return board;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "MatrixEnclosedRegions.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
