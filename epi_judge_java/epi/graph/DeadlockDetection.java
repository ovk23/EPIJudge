package epi.graph;

import epi.graph.DeadlockDetection.GraphVertex.Color;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.List;

public class DeadlockDetection {

    public static class GraphVertex {
        public List<GraphVertex> edges;
        public enum Color { GREY, WHITE, BLACK }
        public Color color;

        public GraphVertex() {
            edges = new ArrayList<>();
        }
    }

    public static boolean isDeadlocked(List<GraphVertex> graph) {
        return graph.stream().anyMatch(v -> v.color == Color.WHITE && hasCycle(v));
    }

    static boolean hasCycle(GraphVertex v) {
        if (v.color == Color.GREY) return true;

        // mark as under scrutiny
        v.color = Color.GREY;

        if (v.edges.stream()
                .anyMatch(next -> next.color != Color.BLACK && hasCycle(next))) {
            return true;
        }

        // mark as visited
        v.color = Color.BLACK;
        return false;
    }

    @EpiUserType(ctorParams = {int.class, int.class})
    public static class Edge {
        public int from;
        public int to;

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }

    @EpiTest(testDataFile = "deadlock_detection.tsv")
    public static boolean isDeadlockedWrapper(TimedExecutor executor,
                                              int numNodes, List<Edge> edges)
            throws Exception {
        if (numNodes <= 0) {
            throw new RuntimeException("Invalid numNodes value");
        }
        List<GraphVertex> graph = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            graph.add(new GraphVertex());
        }
        for (Edge e : edges) {
            if (e.from < 0 || e.from >= numNodes || e.to < 0 || e.to >= numNodes) {
                throw new RuntimeException("Invalid vertex index");
            }
            graph.get(e.from).edges.add(graph.get(e.to));
        }

        return executor.run(() -> isDeadlocked(graph));
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "DeadlockDetection.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
