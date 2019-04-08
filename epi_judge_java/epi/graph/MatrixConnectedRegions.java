package epi.graph;

import epi.graph.SearchMaze.Coordinate;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MatrixConnectedRegions {
    public static void flipColor(int x, int y, List<List<Boolean>> image) {
        helper(image, x, y);
    }
    
    static void helper(List<List<Boolean>> image, int x, int y) {
        boolean color = image.get(x).get(y);
        image.get(x).set(y, !color);

        Queue<Coordinate> q = new ArrayDeque<>();
        q.add(new Coordinate(x, y));
        
        while (!q.isEmpty()) {
            Coordinate curr = q.element();
            
            for (Coordinate c: List.of(new Coordinate(curr.x, curr.y + 1),
                    new Coordinate(curr.x + 1, curr.y),
                    new Coordinate(curr.x - 1, curr.y),
                    new Coordinate(curr.x, curr.y - 1))) {
                if (c.x >= 0 && c.x <= image.size() - 1 && c.y >= 0 && c.y <= image.get(c.x).size() - 1
                    &&  image.get(c.x).get(c.y) == color) {
                    image.get(c.x).set(c.y, !color);
                    q.add(c);
                }
            }

            q.remove();
        }
    }

    @EpiTest(testDataFile = "painting.tsv")
    public static List<List<Integer>> flipColorWrapper(TimedExecutor executor,
                                                       int x, int y,
                                                       List<List<Integer>> image)
            throws Exception {
        List<List<Boolean>> B = new ArrayList<>();
        for (int i = 0; i < image.size(); i++) {
            B.add(new ArrayList<>());
            for (int j = 0; j < image.get(i).size(); j++) {
                B.get(i).add(image.get(i).get(j) == 1);
            }
            
        }

        executor.run(() -> flipColor(x, y, B));

        image = new ArrayList<>();
        for (int i = 0; i < B.size(); i++) {
            image.add(new ArrayList<>());
            for (int j = 0; j < B.get(i).size(); j++) {
                image.get(i).add(B.get(i).get(j) ? 1 : 0);
            }
        }

        return image;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "MatrixConnectedRegions.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
